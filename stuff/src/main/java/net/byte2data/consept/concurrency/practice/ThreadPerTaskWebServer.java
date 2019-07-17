package net.byte2data.consept.concurrency.practice;

import net.byte2data.consept.concurrency.GetDetail;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {
    public static void main(String... args) throws Exception{
        ThreadPerTaskWebServer threadPerTaskWebServer = new ThreadPerTaskWebServer();
        threadPerTaskWebServer.handleRequest();
    }

    private void handleRequest() throws Exception{
        ServerSocket serverSocket = new ServerSocket(8081);
        Runnable task;
        while (true){
            final Socket socket = serverSocket.accept();
            task = new Task(socket);
            new Thread(task).start();
        }
    }

    private class Task implements Runnable{
        private Socket socketResource;
        Task(Socket socket){
            this.socketResource=socket;
        }
        public void run(){
            try{
                InputStream inputStream = socketResource.getInputStream();
                GetDetail.getThreadDetails("input stream read:" +  inputStream.read());
                GetDetail.getThreadDetails("sleeping");
                Thread.sleep(30000);
                GetDetail.getThreadDetails("woken up");
            }catch (Exception ex){
                GetDetail.getThreadDetails(ex.getMessage());
            }
        }
    }
}

