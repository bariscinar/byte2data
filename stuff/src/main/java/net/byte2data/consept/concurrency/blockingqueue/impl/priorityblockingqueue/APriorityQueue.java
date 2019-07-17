package net.byte2data.consept.concurrency.blockingqueue.impl.priorityblockingqueue;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class APriorityQueue {

    private class Resource<T>{
        BlockingQueue<T> blockingQueue=new PriorityBlockingQueue<>();
        List<T> tList=new ArrayList<>(10);
    }

    public void executer() throws InterruptedException{
        Resource<Box> boxResource = new Resource<>();
        for(int index=0; index<100; index++){
            boxResource.tList.add(new Box(new Random().nextInt(5000))) ;
        }
        Producer<Box> boxProducer = new Producer<>(boxResource,boxResource.tList);
        Consumer<Box> boxConsumer = new Consumer<>(boxResource,10);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(boxConsumer);
        executor.submit(boxProducer);
        GetDetail.getThreadDetails("WAITING 3 SECS TO TERMINATE");
        executor.awaitTermination(3000,TimeUnit.MICROSECONDS);
        GetDetail.getThreadDetails("SHUTDOWN NOW");
        List<Runnable> runnables = executor.shutdownNow();
        for(Runnable item : runnables){
            GetDetail.getThreadDetails(item.toString());
        }
        GetDetail.getThreadDetails("COMPLETED");
    }

    public static void main(String... args) throws InterruptedException{
        try{
            APriorityQueue aPriorityQueue = new APriorityQueue();
            aPriorityQueue.executer();
        }catch (Exception ex){
            GetDetail.getThreadDetails(ex.getMessage());
        }

    }

    public class Box implements Comparable<Box>{
        private int size;
        private String name;
        Box(int size){
            this.size=size;
            this.name="Box".concat(String.valueOf(size));
        }

        @Override
        public int compareTo(Box t) {
            int comparedValue=this.size-t.size;
            GetDetail.getThreadDetails(this.name + " and " + t.name +" compare:" + comparedValue);
            return comparedValue;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "size=" + size +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private class Producer<P> implements Runnable{
        private Resource<P> tBlockingQueue;
        private List<P> tList;
        Producer(Resource<P> tPriorityBlockingQueue, List<P> tArrayList){
            this.tBlockingQueue=tPriorityBlockingQueue;
            this.tList=tArrayList;
        }
        public void run(){
            P item;
            for(int index=0; index<this.tList.size(); index++){
                item=this.tList.get(index);
                GetDetail.getThreadDetails("PRODUCING:"+item.toString());
                this.tBlockingQueue.blockingQueue.add(item);

            }
        }
    }

    private class Consumer<C> implements Runnable{
        private Resource<C> cBlockingQueue;
        private int totalConsumingItems;
        Consumer(Resource<C> tPriorityBlockingQueue, int consumingItems){
            this.cBlockingQueue=tPriorityBlockingQueue;
            this.totalConsumingItems=consumingItems;
        }
        public void run(){
            C item;
            for(int index=0; index<this.totalConsumingItems; index++){
                try{
                    Thread.sleep(new Random().nextInt(5000));
                    item=this.cBlockingQueue.blockingQueue.take();
                    GetDetail.getThreadDetails("CONSUMING:"+item.toString());
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
        }
    }

}
