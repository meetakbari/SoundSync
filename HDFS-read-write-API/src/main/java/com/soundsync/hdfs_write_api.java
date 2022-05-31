package com.soundsync;

import java.util.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.mongodb.MongoClient;  
import com.mongodb.client.MongoCollection;  
import com.mongodb.client.MongoDatabase;  
import org.bson.Document; 

import org.bson.Document;
import org.bson.types.ObjectId;


public class hdfs_write_api extends BaseAPIViewSet {
    public hdfs_write_api(HttpReq request) {
        this.request = request;
    }

    @Override
    public void get(HttpReq request) throws Exception {
        HttpResp response = new HttpResp();
        Map<String, String> m = new HashMap<>();
        m.put("Response", "GET method not allowed");
        Gson g = new Gson();
        response.HttpRes200(request, g.toJson(m).toString());
    }

    @Override
    public void post(HttpReq request) throws Exception {
        HttpResp response = new HttpResp();
        Map<String, String> m = new HashMap<>();
        try
        {  
            String song_name = request.FormData.getOrDefault("song_name", "Not Added!").toString();
            String Album_song = request.FormData.getOrDefault("Album", "Not Added!").toString();
            String Composer = request.FormData.getOrDefault("Composer", "Not Added!").toString();
            List<String> Featuring = Arrays.asList(request.FormData.getOrDefault("Featuring", "Not Added!").toString().split("\\s*,\\s*"));
            List<String> Genre = Arrays.asList(request.FormData.getOrDefault("Genre", "Not Added!").toString().split("\\s*,\\s*"));
            List<String> Lyricist = Arrays.asList(request.FormData.getOrDefault("Lyricist", "Not Added!").toString().split("\\s*,\\s*"));
            String Release_date = request.FormData.getOrDefault("Release_date", "Not Added!").toString();
            String Language = request.FormData.getOrDefault("Language", "Not Added!").toString();
            int num_songs = Integer.parseInt(request.FormData.getOrDefault("num_songs", "1").toString());
            // byte[] FileContent = request.FormData.get("file");
            
            MongoClient mongoClient = new MongoClient( "10.1.32.57" , 8000 );  
            MongoDatabase db = mongoClient.getDatabase("sharddemo");  
            MongoCollection<Document> coll1Collection = db.getCollection("movies");  
            // System.out.println("Database connected...");
            // Creating a document for a collection
            Document metaDataDoc = new Document("_id", new ObjectId());
            metaDataDoc.append("song_id", metaDataDoc.get("_id"))
                        .append("song_name",song_name )
                        .append("Album", Album_song)
                        .append("Composer", Composer)
                        .append("Featuring", Featuring)
                        .append("Genre", Genre)
                        .append("Lyricist", Lyricist)
                        .append("Original Release Date", Release_date)
                        .append("Language", Language)
                        .append("Number of songs", num_songs);
            
            
            System.out.println(metaDataDoc.get("_id"));
            ArrayList<InMemoryFile> a = (ArrayList<InMemoryFile>) request.FormData.get("files");
            System.out.println("----> " + a.get(0).FileName.substring(a.get(0).FileName.lastIndexOf(".")+1));
            // InMemoryFile files = Arrays.asList(request.FormData.get("files")).get(0);
            byte[] FileContents =  a.get(0).FileContent;

            // insert operation
            coll1Collection.insertOne(metaDataDoc); 
            
            // String localpath = "/home/mayank/ranjha.mp4";
            
            // URI for HDFS
            String uri = "hdfs://10.20.24.32:9000";
            
            // HDFS directory where song is uploaded
            String hdfsDir = "hdfs://10.20.24.32:9000/test1" + "/" + metaDataDoc.get("_id") + "." +a.get(0).FileName.substring(a.get(0).FileName.lastIndexOf(".")+1);
            
            // Creating configuration object
            Configuration conf = new Configuration();
            
            
            // Filesystem from the HDFS
            FileSystem fs = FileSystem.get(URI.create(uri),conf);
            
            // Copying the localfile to HDFS
            
            // fs.copyFromLocalFile(new Path(localpath),new Path(hdfsDir));

            // FileSystem fs = FileSystem.get(conf);
            Path hdfswritepath = new Path(hdfsDir);
            FSDataOutputStream outputStream=fs.create(hdfswritepath);
            outputStream.write(FileContents);
            outputStream.close();

            
            // String s = fs.getHomeDirectory()+"/chart.png";
            System.out.println("File Uploaded SuccessFully. :)");
            m.put("Response", "File Uploaded Successfully");
            Gson g = new Gson();
            response.HttpRes200(request, g.toJson(m).toString());
        }
        catch(Throwable e){
            m.put("Response", "Error Occured while uploading the file");
            Gson g = new Gson();
            response.HttpRes200(request, g.toJson(m).toString());
            e.printStackTrace();
        }

        
    }
}
