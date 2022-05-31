package cc;

public class Manager {
    public static void main(String[] args) throws Exception {
        new Thread(new App()).start();
        new Thread(new StreamingServer()).start();
    }

}
