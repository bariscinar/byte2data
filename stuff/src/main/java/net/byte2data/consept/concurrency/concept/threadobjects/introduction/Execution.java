package net.byte2data.consept.concurrency.concept.threadobjects.introduction;

public class Execution {

    public static void main(String... args) throws InterruptedException{


        System.out.println("starting main: " + Thread.currentThread().getName() +"/" + Thread.currentThread().getId());

        //WriteSmth writeSmth1 = new WriteSmth("WRITE-1");
        //WriteSmth writeSmth2 = new WriteSmth("WRITE-2");

        DrawSmth drawSmth1 = new DrawSmth("DRAW-1");
        WriteSmth writeSmth1 = new WriteSmth("WRITE-1", Thread.currentThread());
        Thread drawThread = new Thread(drawSmth1);
        Thread writeThread = new Thread(writeSmth1);


        writeThread.start();
        drawThread.start();
        writeThread.join();
        //thread1.start();





        //Thread.sleep(1000);
        System.out.println("completed main: " + Thread.currentThread().getName() +"/" + Thread.currentThread().getId());

    }

}
