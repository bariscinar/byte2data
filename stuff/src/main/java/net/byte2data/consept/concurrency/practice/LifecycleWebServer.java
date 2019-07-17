

package net.byte2data.consept.concurrency.practice;

import net.byte2data.consept.concurrency.GetDetail;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class LifecycleWebServer {
    private static final int NTHREADS=10;
    private static final ExecutorService workers=Executors.newFixedThreadPool(NTHREADS);


    public static void main(String... args) throws Exception{
        LifecycleWebServer lifecycleWebServer = new LifecycleWebServer();
        lifecycleWebServer.handleRequest();
    }

    private void handleRequest() throws Exception{
        ServerSocket serverSocket = new ServerSocket(8081);
        Runnable task;
        while (true){
            final Socket socket = serverSocket.accept();
            task = new Task(socket);
            workers.execute(task);
            //new WithinThreadExecutor().execute(task);
            //new ThreadPerTaskExecutor().execute(task);
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
                Thread.sleep(50000);
                GetDetail.getThreadDetails("woken up");
            }catch (Exception ex){
                GetDetail.getThreadDetails(ex.getMessage());
            }
        }
    }

}

