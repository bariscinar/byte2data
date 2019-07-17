package net.byte2data.consept.concurrency.blockingqueue.impl.delayqueue;


import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SimplestDelayQueue {

    private void executer(){

        BlockingQueue<DelayedItem> blockingQueue = new DelayQueue<>();


        List<DelayedItem> delayedItemList = new ArrayList<>();

        int randomVar;
        for(int index=0; index<1000; index++){
            randomVar=new Random().nextInt(1);
            delayedItemList.add(new DelayedItem(String.valueOf(randomVar),randomVar,randomVar));
        }

        Producer integerProducer = new Producer(blockingQueue,delayedItemList);
        Consumer integerConsumer = new Consumer(blockingQueue);

        Thread producerThread = new Thread(integerProducer);
        Thread consumerThread = new Thread(integerConsumer);

        producerThread.start();
        consumerThread.start();
    }

    public static void main(String... args){
        SimplestDelayQueue anArrayBlockingQueue = new SimplestDelayQueue();
        anArrayBlockingQueue.executer();
    }



    private class Producer implements Runnable {
        private BlockingQueue<DelayedItem> blockingQueueResource;
        private List<DelayedItem> delayedItemList;

        public Producer(BlockingQueue<DelayedItem> blockingQueue, List<DelayedItem> delayedItemList) {
            this.blockingQueueResource=blockingQueue;
            this.delayedItemList=delayedItemList;
        }

        public void run() {
            try {
                for(DelayedItem item : delayedItemList){
                    Thread.sleep(new Random().nextInt(200));
                    //GetDetail.getThreadDetails("Producing:"+item.name +" - Result:" + blockingQueueResource.offer(item));
                    GetDetail.getThreadDetails("Producing:"+item.name);
                    blockingQueueResource.offer(item);
                }
            } catch (InterruptedException ie) {
                GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to put");
            }

        }
    }

    private class Consumer implements Runnable{
        private BlockingQueue<DelayedItem> blockingQueueResource;
        public Consumer(BlockingQueue<DelayedItem> blockingQueue){
            this.blockingQueueResource=blockingQueue;
        }
        public void run(){
            int waitingPeriod;
            for(int index=0; index<100; index++){
                //waitingPeriod = new Random().nextInt(501);
                try{
                    //GetDetail.getThreadDetails("Consumer is waiting:"+waitingPeriod + " msec to get...");
                    //Thread.sleep(1000);
                    //GetDetail.getThreadDetails("Consumer result: " +blockingQueueResource.poll());
                    GetDetail.getThreadDetails("Consuming:" +blockingQueueResource.take().name);
                }catch (Exception ie){
                    GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to get");
                }
            }
        }
    }

    private class DelayedItem implements Delayed{
        private Delayed delayedItem;
        private double size;
        private String name;
        private long startTime;
        DelayedItem(String name, double size, long startTime){
            this.name=name;
            this.size=size;
            this.startTime=System.currentTimeMillis() + startTime;
            this.delayedItem=this;
        }
        @Override
        public long getDelay(TimeUnit unit) {
            long diff=startTime-System.currentTimeMillis();
            return unit.convert(diff,TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            //return Ints.saturatedCast(this.startTime - ((DelayedItem) o).startTime);
            return 0;
        }

        @Override
        public String toString() {
            return "DelayedItem{" +
                    "delayedItem=" + delayedItem +
                    ", size=" + size +
                    ", name='" + name + '\'' +
                    ", startTime=" + startTime +
                    '}';
        }
    }
}
