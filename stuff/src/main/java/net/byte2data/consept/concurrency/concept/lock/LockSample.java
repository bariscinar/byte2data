package net.byte2data.consept.concurrency.concept.lock;

import java.util.LinkedList;
import java.util.List;

public class LockSample {
    private static void getDetails(String message){
        Thread currentThread = Thread.currentThread();
        String thredName=currentThread.getName();
        long threadId=currentThread.getId();
        int threadPriority=currentThread.getPriority();
        System.out.format("%s:%s:%s - %s%n",threadId,thredName,threadPriority,message);
    }
    public void executer(){
        Resource resource=new Resource(0,100);
        Consumer consumer = new Consumer(resource);
        Producer producer = new Producer(resource);

        Thread threadConsumer1 = new Thread(consumer);
        Thread threadConsumer2 = new Thread(consumer);
        Thread threadConsumer3 = new Thread(consumer);
        Thread threadConsumer4 = new Thread(consumer);
        Thread threadProducer1 = new Thread(producer);

        threadConsumer1.start();
        threadConsumer2.start();
        threadProducer1.start();
        threadConsumer3.start();
        threadConsumer4.start();

    }
    public static void main(String... args){
        LockSample lockSample = new LockSample();
        lockSample.executer();

    }
    private final class Lock{
        private volatile boolean lockState=true;
        synchronized void lock() throws InterruptedException{
            while (lockState){
                getDetails("waiting...");
                wait();
            }
            this.lockState=true;
        }
        synchronized void unLock(){
            getDetails("notifiying...");
            this.lockState=false;
            notify();
        }
    }
    private class Resource{
        private int minSizeOfQueue;
        private int maxSizeOfQueue;
        private List<Integer> fifoQueue;
        private String localValue;
        private Lock localLock;
        Resource(int min, int max){
            this.minSizeOfQueue=min;
            this.maxSizeOfQueue=max;
            fifoQueue=new LinkedList<>();
            localValue=null;
            localLock=new Lock();
        }
        void enQueue(String val){
            try{
                this.localLock.lock();
                this.localValue=val;
                getDetails(this.localValue);
                this.localLock.unLock();
            }catch (InterruptedException ie){
                getDetails(ie.getMessage());
            }
        }
        void deQueue(){
            try{
                this.localLock.lock();
                getDetails(this.localValue);
                this.localLock.unLock();
            }catch (InterruptedException ie){
                getDetails(ie.getMessage());
            }
        }
    }

    private class Producer implements Runnable{
        private Resource resource;
        Producer(Resource queueResource){
            this.resource=queueResource;
        }
        public void run(){
            try{
                Thread.sleep((int)(Math.random()*10000));
                resource.enQueue("1");
            }catch (InterruptedException ie){
                getDetails(ie.getMessage());
            }

        }
    }

    private class Consumer implements Runnable{
        private Resource resource;
        Consumer(Resource queueResource){
            this.resource=queueResource;
        }
        public void run(){
            try{
                Thread.sleep((int)(Math.random()*10000));
                resource.deQueue();
            }catch (InterruptedException ie){
                getDetails(ie.getMessage());
            }

        }
    }
}
