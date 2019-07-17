package net.byte2data.consept.concurrency.blockingqueue.impl.delayqueue;



import net.byte2data.consept.concurrency.GetDetail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ADelayedQueueSample {
    private BlockingQueue<Feeder> feederBlockingQueue;
    private Resource resource;
    ADelayedQueueSample(){
        feederBlockingQueue=new DelayQueue<Feeder>();
        resource=new Resource(999l);
    }
    public void executer(){

        Producer producer = new Producer(feederBlockingQueue,resource);
        Consumer consumer = new Consumer(feederBlockingQueue);
        /*
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
        */
        for(int index=0; index<1000; index++){
            if(index%10==0)
                //new Thread(producer,String.valueOf(index)).start();
                new Thread(new Producer(feederBlockingQueue,resource),String.valueOf(index)).start();
            //new Thread(consumer,String.valueOf(index)).start();
            new Thread(new Consumer(feederBlockingQueue),String.valueOf(index)).start();

        }
    }

    public static void main(String... args){
        ADelayedQueueSample aDelayedQueueSample = new ADelayedQueueSample();
        aDelayedQueueSample.executer();
    }

    private class Resource{
        private long indexValue;
        Resource(long initialValue){
            indexValue=initialValue;
        }
        synchronized long getLastIndex(){
            return ++indexValue;
        }
    }

    private class Feeder implements Delayed{
        private long releaseTime;
        private String name;
        Feeder(long waitingPeriod){
            this.name=String.valueOf(waitingPeriod);
            this.releaseTime=System.currentTimeMillis()+waitingPeriod;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            //long diff = this.releaseTime - System.currentTimeMillis();
            //return unit.convert(diff,TimeUnit.MILLISECONDS);
            return this.releaseTime - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            //return Ints.saturatedCast(this.releaseTime - ((Feeder) o).releaseTime);
            //return Ints.saturatedCast( ((Feeder) o).releaseTime-this.releaseTime);
            return 0;
        }

        @Override
        public String toString() {
            return "Feeder{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private class Producer implements Runnable{
        private BlockingQueue<Feeder> delayedBlockingQueue;
        private Resource resource;
        Producer(BlockingQueue<Feeder> blockingQueue, Resource resource){
            this.delayedBlockingQueue=blockingQueue;
            this.resource=resource;
        }
        public void run(){
            Feeder localFeed = generateDelayObject();
            GetDetail.getThreadDetails("PRODUCING:"+localFeed.toString());
            delayedBlockingQueue.offer(localFeed);
        }

        private Feeder generateDelayObject(){
            return new Feeder(resource.getLastIndex());
        }
    }

    private class Consumer implements Runnable{
        private BlockingQueue<Feeder> delayedBlockingQueue;
        Consumer(BlockingQueue<Feeder> blockingQueue){
            this.delayedBlockingQueue=blockingQueue;
        }
        public void run(){
            try{
                GetDetail.getThreadDetails("CONSUMING:"+delayedBlockingQueue.take().toString());
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }

        }


    }
}
