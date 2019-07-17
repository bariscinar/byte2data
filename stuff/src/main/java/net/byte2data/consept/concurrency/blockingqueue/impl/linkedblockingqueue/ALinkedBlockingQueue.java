package net.byte2data.consept.concurrency.blockingqueue.impl.linkedblockingqueue;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ALinkedBlockingQueue {

    private void executer(){
        //Resource resource = new Resource(10);
        BlockingQueue<Integer> integerBlockingQueue = new LinkedBlockingQueue<>(10);
        List<Integer> integerList = new ArrayList<>();
        for(int index=0; index<100; index++){
            integerList.add(new Random().nextInt(10001));
        }
        Producer<Integer> integerProducer = new Producer<>(integerBlockingQueue,integerList);
        Consumer<Integer> integerConsumer = new Consumer<>(integerBlockingQueue);

        Thread producerThread = new Thread(integerProducer);
        Thread consumerThread = new Thread(integerConsumer);

        producerThread.start();
        consumerThread.start();
    }

    public static void main(String... args){
        ALinkedBlockingQueue anArrayBlockingQueue = new ALinkedBlockingQueue();
        anArrayBlockingQueue.executer();
    }

    private class Resource<J>{
        private BlockingQueue<J> blockingQueue;
        private int queueLimit;
        Resource(int limit){
            this.queueLimit=limit;
            blockingQueue=new ArrayBlockingQueue<J>(limit);
        }
    }

    private class Producer<J> implements Runnable{
        private BlockingQueue<J> blockingQueueResource;
        private List<J> producerList;
        public Producer(BlockingQueue<J> blockingQueue, List<J> list){
            this.blockingQueueResource=blockingQueue;
            this.producerList=list;
        }
        public void run(){
            int waiting=0;
            for(J item : producerList){
                try{
                    GetDetail.getThreadDetails("Producing:"+item);
                    //waiting=new Random().nextInt(501);
                    //Thread.sleep(waiting);
                    blockingQueueResource.put(item);
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to put");
                }
            }
        }
    }

    private class Consumer<J> implements Runnable{
        private BlockingQueue<J> blockingQueueResource;
        public Consumer(BlockingQueue<J> blockingQueue){
            this.blockingQueueResource=blockingQueue;
        }
        public void run(){
            int waiting = 0;
            for(int index=0; index<1000; index++){
                try{
                    waiting = new Random().nextInt(5001);
                    //GetDetail.getThreadDetails("Consumer waiting:"+waiting + " msec to get...");
                    Thread.sleep(waiting);
                    GetDetail.getThreadDetails("Consuming:" +blockingQueueResource.take());

                }catch (Exception ie){
                    GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to get");
                }
            }
        }
    }
}
