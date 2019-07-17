package net.byte2data.consept.concurrency.blockingqueue.impl.arrayblockingqueue;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SimplestArrayBlockingQueue {

    private void executer() throws Exception{
        BlockingQueue<Integer> integerBlockingQueue = new ArrayBlockingQueue<Integer>(6);
        List<Integer> integerList = new ArrayList<>();
        for(int index=0; index<100; index++){
            integerList.add(new Random().nextInt(10001));
        }

        Producer integerProducer = new Producer(integerBlockingQueue);
        Consumer integerConsumer = new Consumer(integerBlockingQueue);

        Thread producerThread = new Thread(integerProducer);
        Thread consumerThread = new Thread(integerConsumer);

        producerThread.start();
        Thread.sleep(5500l);
        consumerThread.start();
    }

    public static void main(String... args) throws Exception{
        SimplestArrayBlockingQueue anArrayBlockingQueue = new SimplestArrayBlockingQueue();
        anArrayBlockingQueue.executer();
    }



    private class Producer implements Runnable {
        private BlockingQueue<Integer> blockingQueueResource;

        public Producer(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueueResource = blockingQueue;
        }

        public void run() {
            int increamentValue=0;
            for(int index=0; index<7; index++){
                try {
                    GetDetail.getThreadDetails("PUT Result: " + ++increamentValue);
                    blockingQueueResource.put(increamentValue);
                    //GetDetail.getThreadDetails("ADD Result: " + ++increamentValue + " - " + blockingQueueResource.addToTail(increamentValue));
                    GetDetail.getThreadDetails("OFFER Result: " + ++increamentValue + " - " + blockingQueueResource.offer(increamentValue));
                    GetDetail.getThreadDetails("OFFER - TimeOut Result: " + ++increamentValue + " - " + blockingQueueResource.offer(increamentValue ,2, TimeUnit.SECONDS));
                } catch (Exception ie) {
                    GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to put:" + increamentValue);
                }
            }
        }
    }

    private class Consumer implements Runnable{
        private BlockingQueue<Integer> blockingQueueResource;
        public Consumer(BlockingQueue<Integer> blockingQueue){
            this.blockingQueueResource=blockingQueue;
        }
        public void run(){
            for(int index=0; index<7; index++){
                try{
                    GetDetail.getThreadDetails("TAKE Result: " + blockingQueueResource.take());
                    //GetDetail.getThreadDetails("REMOVE Result: " + blockingQueueResource.removeFromTail());
                    GetDetail.getThreadDetails("ELEMENT Result: " + blockingQueueResource.element());
                    GetDetail.getThreadDetails("PEEK Result: " + blockingQueueResource.peek());
                    GetDetail.getThreadDetails("POLL Result: " + blockingQueueResource.poll());
                    GetDetail.getThreadDetails("POLL - TimeOut Result: " + blockingQueueResource.poll(3,TimeUnit.SECONDS));
                }catch (Exception ie){
                    GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to get");
                }
            }
        }
    }
}
