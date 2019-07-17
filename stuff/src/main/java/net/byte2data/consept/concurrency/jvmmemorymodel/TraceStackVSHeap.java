package net.byte2data.consept.concurrency.jvmmemorymodel;

public class TraceStackVSHeap {

    public static void main(String... args) throws InterruptedException{
        MySharedObject mySharedObjectOutside = new MySharedObject();
        Runner runner1 = new Runner(mySharedObjectOutside);
        //Runner runner2 = new Runner(mySharedObjectOutside);

        Thread thread1 = new Thread(runner1);
        //Thread thread2 = new Thread(runner2);
        Thread thread2 = new Thread(runner1);

        thread1.start();
        //thread1.join();
        //thread1.start();
        thread2.start();
    }






}
