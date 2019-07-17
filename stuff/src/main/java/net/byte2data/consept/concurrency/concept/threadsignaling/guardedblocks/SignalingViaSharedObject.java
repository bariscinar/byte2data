package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SignalingViaSharedObject {

    public static final class Locking{
        private volatile int z=0;
        private volatile boolean hasDataToProcess;
        synchronized boolean  getProcessStatus(){
            return this.hasDataToProcess;
        }
        synchronized void setProcessStatus(boolean state){
            this.hasDataToProcess=state;
        }
        Locking(){
            hasDataToProcess=false;
        }
    }



    public static void getDetail(String message){
        System.out.format("%s: %s%n",Thread.currentThread().getName(),message);
    }

    public void executer(){

        SharedResource sharedResource = new SharedResource();

        Producer producer1 = new Producer(sharedResource);
        Producer producer2 = new Producer(sharedResource);
        Producer producer3 = new Producer(sharedResource);
        Producer producer4 = new Producer(sharedResource);
        Producer producer5 = new Producer(sharedResource);
        Producer producer6 = new Producer(sharedResource);
        Producer producer7 = new Producer(sharedResource);
        Producer producer8 = new Producer(sharedResource);
        Consumer consumer1 = new Consumer(sharedResource);

        Thread producerThread1 = new Thread(producer1);
        Thread producerThread2 = new Thread(producer1);
        Thread producerThread3 = new Thread(producer1);
        Thread producerThread4 = new Thread(producer1);
        Thread producerThread5 = new Thread(producer1);
        Thread producerThread6 = new Thread(producer1);
        Thread producerThread7 = new Thread(producer1);
        Thread producerThread8 = new Thread(producer1);

        Thread consumerThread1 = new Thread(consumer1);
        Thread consumerThread2 = new Thread(consumer1);
        Thread consumerThread3 = new Thread(consumer1);

        consumerThread1.start();
        consumerThread2.start();
        consumerThread3.start();

        producerThread1.start();
        producerThread2.start();
        producerThread3.start();
        producerThread4.start();
        producerThread5.start();
        producerThread6.start();
        producerThread7.start();
        producerThread8.start();


    }

    public static void main(String... args){
        SignalingViaSharedObject signalingViaSharedObject = new SignalingViaSharedObject();
        signalingViaSharedObject.executer();
    }

    class Producer implements Runnable{
        private SharedResource sharedResource;
        private volatile AtomicInteger localIndex = new AtomicInteger(0);
        ThreadLocal<Integer> integerThreadLocal;
        Producer(SharedResource sr){
            this.sharedResource=sr;
            integerThreadLocal=new ThreadLocal<>();
        }
        public void run(){
            integerThreadLocal.set(localIndex.addAndGet(1));
            int x = integerThreadLocal.get();
            getDetail("Producer.run is being starting with:"+ localIndex.get() + " and box:" +x);
            sharedResource.put(new Box(x,x,x,x));
            getDetail("Producer.run has completed with:"+ localIndex.get() + " and box:" +x);
        }
    }

    class Consumer implements Runnable{
        private SharedResource sharedResource;
        Consumer(SharedResource sr){
            this.sharedResource=sr;
        }
        public void run(){
            getDetail("Consumer.run is being starting");
            getDetail(sharedResource.get().toString());
            getDetail("Consumer.run has completed");
        }
    }

    private enum Dimensions{
        HEIGHT(0),WIDTH(0),LENGTH(0);
        private double value;
        Dimensions(double value){
            this.value=value;
        }
        public double getValue(){
            return this.value;
        }
        public void setValue(double newVal){
            this.value=newVal;
        }

    }
    class Box{
        private int height, width, length, index;
        private Dimensions HEIGHT = Dimensions.HEIGHT;
        private Dimensions WIDTH = Dimensions.WIDTH;
        private Dimensions LENGTH = Dimensions.LENGTH;

        Box(int x, int y, int z, int index){

            this.height=x;
            this.width=y;
            this.length=z;

            HEIGHT.setValue(this.height);
            WIDTH.setValue(this.width);
            LENGTH.setValue(this.length);

            this.index=index;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "height=" + height +
                    ", width=" + width +
                    ", length=" + length +
                    ", index=" + index +
                    ", HEIGHT=" + HEIGHT.getValue() +
                    ", WIDTH=" + WIDTH.getValue() +
                    ", LENGTH=" + LENGTH.getValue() +
                    '}';
        }
    }

    class SharedResource{
        private List<Box> boxList;
        private final Locking lockingObject;

        SharedResource(){
            boxList=new ArrayList<>();
            lockingObject=new Locking();
        }

        void put(Box box){
            synchronized (lockingObject){
                try{
                    while(lockingObject.getProcessStatus()) {
                        getDetail("WAIT for PUTTING");
                        //Thread.sleep(5000);
                        //getDetail("WAITED 5 secs");
                        lockingObject.wait();
                    }
                    boxList.add(box);
                    getDetail("PUTTING has completed: " + box.toString());
                    lockingObject.setProcessStatus(true);
                    getDetail("Other Threads are NOTIFIED about PUTTING has completed");
                    lockingObject.notifyAll();

                }catch (InterruptedException ie){
                    getDetail(ie.getMessage());
                }
            }

        }

        Box get(){
            synchronized (lockingObject){
                Box localBox = null;
                try {
                    while(!lockingObject.getProcessStatus()) {
                        getDetail("WAIT for GETTING");
                        //Thread.sleep(5000);
                        //getDetail("WAITED 5 secs");
                        lockingObject.wait();
                    }
                    localBox = boxList.get(0);
                    getDetail("GETTING has completed: " + localBox.toString());
                    lockingObject.setProcessStatus(false);
                    getDetail("Other Threads are NOTIFIED about GETTING has completed");
                    lockingObject.notifyAll();

                }catch (InterruptedException ie){
                    getDetail(ie.getMessage());
                }
                return localBox;
            }
        }
    }

}
