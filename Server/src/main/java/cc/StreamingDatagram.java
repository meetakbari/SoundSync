package cc;

public class StreamingDatagram {
    private String song_id="";
    private int no=0;
    private byte[] packet_bytes;

    public StreamingDatagram(){
    }
    public StreamingDatagram(int no){
        super();
        this.no=no;
    }
    public StreamingDatagram(byte[] packet_bytes){
        super();
        this.packet_bytes=packet_bytes;
    }
    public StreamingDatagram(String song_id){
        super();
        this.song_id = song_id;
    }
    public StreamingDatagram(int no,byte[] packet_bytes){
        super();
        this.no=no;
        this.packet_bytes=packet_bytes;
    }

    public StreamingDatagram(int no,byte[] packet_bytes, String song_id){
        super();
        this.no=no;
        this.packet_bytes=packet_bytes;
        this.song_id = song_id;
    }

    public byte[] getPacket_bytes() {
         return packet_bytes;
     }
    public void setPacket_bytes(byte[] packet_bytes) {
         this.packet_bytes = packet_bytes;
     }
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }

    public String getSong_id(){
        return this.song_id;
    }

    public void setSong_id( String song_id){
        this.song_id = song_id;
    }
}
