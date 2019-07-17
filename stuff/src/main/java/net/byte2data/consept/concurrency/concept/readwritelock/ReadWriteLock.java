package net.byte2data.consept.concurrency.concept.readwritelock;

public class ReadWriteLock {

    private static void getDetails(String message){
        System.out.format("%s/%s -> %s%n", Thread.currentThread().getName(),Thread.currentThread().getId(),message);
    }

    private int readers;
    private int writers;
    private int writeRequests;

    public synchronized void lockRead(){
        try{
            if(writers>0 || writeRequests>0){
                getDetails("lockRead -> WAITING...");
                wait();
                getDetails("lockRead -> Wait DONE!");
            }
            getDetails("lockRead -> Granted!");
            readers++;
            //getDetails("lockRead -> NOTIFYING...");
            //notify();
            //getDetails("lockRead -> Notification DONE!");
        }catch (InterruptedException ie){
            getDetails(ie.getMessage());
        }
    }
    public synchronized void unlockRead(){
        try{
            readers--;
            getDetails("unlockRead -> NOTIFYING...");
            notifyAll();
            getDetails("unlockRead -> Notification DONE!");
        }catch (Exception ie){
            getDetails(ie.getMessage());
        }
    }

    public synchronized void lockWrite(){
        try{
            writeRequests++;
            if(readers>0 || writers>0){
                getDetails("lockWrite -> WAITING...");
                wait();
                getDetails("lockWrite -> Wait DONE!");
            }
            getDetails("lockWrite -> Granted!");
            writeRequests--;
            writers++;
            //getDetails("lockWrite -> NOTIFYING...");
            //notifyAll();
            //getDetails("lockWrite -> Notification DONE!");
        }catch (InterruptedException ie){
            getDetails(ie.getMessage());
        }
    }
    public synchronized void unlockWrite(){
        try{
            writers--;
            getDetails("unlockWrite -> NOTIFYING...");
            notifyAll();
            getDetails("unlockWrite -> Notification DONE!");
        }catch (Exception ie){
            getDetails(ie.getMessage());
        }
    }

}
