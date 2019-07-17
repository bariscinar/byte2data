package net.byte2data.consept.concurrency.blockingqueue.impl.delayqueue;


import net.byte2data.consept.concurrency.GetDetail;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueSample {

    private BlockingQueue<DelayedObject> delayedObjects;

    DelayQueueSample(){
        delayedObjects=new DelayQueue<>();
    }

    public void test(){
        try{
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            int numberOfElementsToProduce=2;
            int delayOfEachProducedElementMiliseconds=10;
            Producer producer = new Producer(delayedObjects,numberOfElementsToProduce,delayOfEachProducedElementMiliseconds);
            Consumer consumer = new Consumer(delayedObjects,numberOfElementsToProduce);
            executorService.submit(producer);
            executorService.submit(producer);
            executorService.submit(producer);
            executorService.submit(producer);
            executorService.submit(consumer);
            executorService.submit(consumer);
            executorService.submit(consumer);
            executorService.submit(consumer);
            executorService.submit(consumer);
            executorService.submit(consumer);

            executorService.awaitTermination(10000,TimeUnit.MILLISECONDS);
            GetDetail.getThreadDetails("is shut down:" + executorService.isShutdown());
            executorService.shutdownNow();
            //executorService.shutdown();
            GetDetail.getThreadDetails("is shut down:" + executorService.isShutdown());
            Thread.sleep(5000);
            GetDetail.getThreadDetails("consumed elements:"+consumer.numberOfConsumedElements);
        }catch (InterruptedException ie){
            GetDetail.getThreadDetails(ie.getMessage());
        }
    }

    public static void main(String... args){
        DelayQueueSample delayQueueSample = new DelayQueueSample();
        delayQueueSample.test();
    }

    private class Producer implements Runnable{
        private BlockingQueue<DelayedObject> delayedObjectBlockingQueue;
        private int totalProducedObjectCount;
        private int objectTTL;
        Producer(BlockingQueue<DelayedObject> delayedObjects,int producedObjectCount, int objectTTL){
            this.delayedObjectBlockingQueue=delayedObjects;
            this.totalProducedObjectCount =producedObjectCount;
            this.objectTTL=objectTTL;
        }
        public void run(){
            DelayedObject tempObj;
            for(int index = 0; index< totalProducedObjectCount; index++){
                try{

                    tempObj = new DelayedObject(UUID.randomUUID().toString(),objectTTL);
                    GetDetail.getThreadDetails("PRODUCING:"+tempObj.name);
                    delayedObjectBlockingQueue.put(tempObj);

                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }

            }
        }
    }

    private class Consumer implements Runnable{
        private BlockingQueue<DelayedObject> delayedObjectBlockingQueue;
        private int totalConsumedObjectCount;
        public AtomicInteger numberOfConsumedElements;
        Consumer(BlockingQueue<DelayedObject> delayedObjects, int consumedObjectCount) {
            this.delayedObjectBlockingQueue=delayedObjects;
            this.totalConsumedObjectCount =consumedObjectCount;
            this.numberOfConsumedElements = new AtomicInteger();
        }
        public void run(){
            for(int index = 0; index< totalConsumedObjectCount; index++){
                try{
                    GetDetail.getThreadDetails("CONSUMING:"+delayedObjectBlockingQueue.take().name);
                    numberOfConsumedElements.incrementAndGet();
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }

            }
        }
    }

    private class DelayedObject implements Delayed{
        private String name;
        private long expireTimestamp;
        DelayedObject(String oName, long oExpire){
            this.name=oName;
            this.expireTimestamp=System.currentTimeMillis()+oExpire;

        }
        @Override
        public long getDelay(TimeUnit unit) {
            long diff = this.expireTimestamp-System.currentTimeMillis();
            GetDetail.getThreadDetails("diff:"+diff);
            GetDetail.getThreadDetails("converted:"+unit.convert(diff,TimeUnit.MILLISECONDS));
            return unit.convert(diff,TimeUnit.MILLISECONDS);
            //return diff;
        }

        @Override
        public int compareTo(Delayed o) {
            //return Ints.saturatedCast(this.expireTimestamp-(((DelayedObject)o).expireTimestamp));
            return 0;
        }
    }
}
