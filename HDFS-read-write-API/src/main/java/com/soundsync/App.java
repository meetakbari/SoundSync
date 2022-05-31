package com.soundsync;

import java.io.*;
import java.net.*;
import java.net.InetAddress;

// Server class
class App {
    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        InetAddress addr = InetAddress.getByName("0.0.0.0");
        try {

            server = new ServerSocket(8000, 50, addr);
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();
                ClientHandler clientSock = new ClientHandler(client);
                System.out.println("new client created");
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            HttpReq request = null;
            try {
                request = new HttpReq(clientSocket);
                Routers route = new Routers(request);
                Runnable ClassObject = route.getObject();
                if (ClassObject != null)
                    ClassObject.run();
                else
                    new HttpResp().HttpRes404(request);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!clientSocket.isClosed())
                        new HttpResp().HttpRes500(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}