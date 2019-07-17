package net.byte2data.consept.concurrency.practice;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadWebServer {
    public static void main(String... args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true){
            final Socket socket = serverSocket.accept();
            handleRequest(socket);
        }
    }
    private static void handleRequest(Socket socket) throws Exception{
        InputStream inputStream = socket.getInputStream();
        System.out.println(inputStream.read());
        System.out.println("sleeping");
        Thread.sleep(10000);
        System.out.println("woken up");
    }
}
