package cc;

import com.corundumstudio.socketio.AckRequest;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import java.nio.file.Files;
// import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.Arrays;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import java.net.URI;

public class StreamingServer implements Runnable {
    private  com.corundumstudio.socketio.Configuration SocketConfig;
    private  org.apache.hadoop.conf.Configuration HadoopConfig;
    
    public byte[] subArray(byte[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9002);
        
        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                // TODO Auto-generated method stub
                System.out.println("client connected");    
            }
            
        });
        server.addEventListener("get_packet", StreamingDatagram.class, new DataListener<StreamingDatagram>() {
            @Override
            public void onData(SocketIOClient client, StreamingDatagram data, AckRequest ackSender) throws Exception {
            
                    System.out.println(data.getSong_id());
                    
                    String uri = "hdfs://10.20.24.32:9000/song_store/"+ data.getSong_id() +".mp3";
        
                    // Configuration
                    org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
                    
                    // Reading the Filesystem
                    FileSystem fs = FileSystem.get(URI.create(uri),conf);
                    
                    // InputStream for reading the files in chunks or packets
                    InputStream in = null;
                    // System.out.println("\n\nFile Content : \n");
                       
                    // Simple read txt
                    in = fs.open(new Path(uri));
                   
                    byte[] fileContent = in.readAllBytes();
                    
                    int chunk_size =60000;

                    for(int i=0;i<fileContent.length;i+=chunk_size){
                        byte[] chunk = subArray(fileContent, i, Math.min(fileContent.length,i+chunk_size-1)) ; 
                        StreamingDatagram datagram = new StreamingDatagram(i/chunk_size,chunk);
                        client.sendEvent("data_packet", datagram);
                    }
            }
        });  

        server.start();

        // try {
        //     Thread.sleep(Integer.MAX_VALUE);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // server.stop();
    }

}