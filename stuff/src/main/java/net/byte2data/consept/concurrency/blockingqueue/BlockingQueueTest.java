package net.byte2data.consept.concurrency.blockingqueue;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueTest {


    int index;

    public interface Generator<J>{
        abstract J generatedItem();
    }

    public static class Box{
        int size;
        String name;

        public Box(){
            this(new Random().nextInt(999),"bir");
        }

        Box(int size, String name){
            this.size=size;
            this.name=name;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "size=" + size +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    private class QueueResource<J>{
        private final int maxQueueSize;
        private final int minQueueSize;
        private List<J> queueList;

        QueueResource(int size){
            queueList=new LinkedList<>();
            this.maxQueueSize=size;
            this.minQueueSize=0;

        }

        private boolean waitForPush(){
            return getSize().intValue()>=maxQueueSize;
        }

        private boolean waitForPop(){
            return getSize().intValue()<=minQueueSize;
        }

        private boolean notifyAfterPush(){
            return getSize().intValue()<=minQueueSize;
        }

        private boolean notifyAfterPop(){
            return getSize().intValue()>=maxQueueSize;
        }

        private synchronized AtomicInteger getSize(){
            return new AtomicInteger(queueList.size()) ;
        }

        synchronized void push(J item){
            try {
                while (waitForPush()){
                    GetDetail.getThreadDetails("PUT -> Waiting");
                    wait();
                    GetDetail.getThreadDetails("PUT -> Waiting Completed");
                }
                GetDetail.getThreadDetails("PUT ->" + item.toString());
                queueList.add(item);
                /*
                Notice how notifyAll() is only called from enqueue() and dequeue()
                if the resource size is equal to the size bounds (0 or limit).
                If the resource size is not equal to either bound when enqueue() or dequeue() is called,
                there can be no threads waiting to either enqueue or dequeue items.
                */
                /*
                NOTE:
                My experiments proves that there should not be a condition here!
                 */
                if(notifyAfterPush()){
                    notify();
                    GetDetail.getThreadDetails("PUT -> Notified");
                }
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
                }
        }

        synchronized J pop(){
            J item = null;
            try {
                while (waitForPop()){
                    GetDetail.getThreadDetails("GET -> Waiting");
                    wait();
                    GetDetail.getThreadDetails("GET -> Waiting Completed");
                }
                item =  queueList.remove(minQueueSize);
                GetDetail.getThreadDetails("GET ->" + item.toString());
                if(notifyAfterPop()){
                    //popLocking.notify();
                    notify();
                    GetDetail.getThreadDetails("GET -> Notified");
                }
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
            return item;
        }
    }

    private class Producer<J> implements Runnable{
        private QueueResource<J> resource;
        private J putItem;
        Producer(QueueResource<J> queueResource, Class<J> jClass){
            System.out.println("producer constructor called!");
            this.resource=queueResource;
            try {
                this.putItem = jClass.newInstance();
                System.out.println("generated new item:"+putItem);
            }catch (InstantiationException|IllegalAccessException ex){
                GetDetail.getThreadDetails("check this out: " + ex.getMessage());
            }
        }
        public void run(){
            try{
                Thread.sleep((int)(Math.random()*10000));
                resource.push(putItem);
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }

        }
    }

    private class Consumer<J> implements Runnable{
        private QueueResource<J> resource;
        Consumer(QueueResource<J> queueResource){
            this.resource=queueResource;
        }
        public void run(){
            try{
                Thread.sleep((int)(Math.random()*10000));
                resource.pop();
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }

        }
    }

    public void executer()throws InterruptedException{

        QueueResource<Box> boxQueueResource = new QueueResource<>(3);

        List<Producer<Box>> producerList = new ArrayList<>();

        List<Thread> threadListForProducer = new ArrayList<>();
        List<Thread> threadListForConsumer = new ArrayList<>();

        Producer<Box> idleProducerItem;
        for(index=0; index<10; index++){
            /*
            idleProducerItem = new Producer<>(boxQueueResource, new Generator<Box>() {
                @Override
                public Box generatedItem() {
                    return new Box(index,"name-"+String.valueOf(index));
                }
            });
            */
            idleProducerItem = new Producer<>(boxQueueResource,Box.class);

            producerList.add(idleProducerItem);
            threadListForProducer.add(new Thread(idleProducerItem));
            threadListForConsumer.add(new Thread(new Consumer<>(boxQueueResource)));
            threadListForConsumer.add(new Thread(new Consumer<>(boxQueueResource)));
            threadListForConsumer.add(new Thread(new Consumer<>(boxQueueResource)));
            threadListForConsumer.add(new Thread(new Consumer<>(boxQueueResource)));
        }

        for(int x=0; x<10; x++){
            threadListForConsumer.get(x).start();
            threadListForProducer.get(x).start();

        }

        for(int x=10; x<40; x++){
            threadListForConsumer.get(x).start();

        }

        Thread.sleep(15000);
        System.out.println("*****");
        Box xBox;
        while(boxQueueResource.queueList.iterator().hasNext()){
            xBox=boxQueueResource.queueList.iterator().next();
            GetDetail.getThreadDetails(xBox.toString());
            boxQueueResource.queueList.remove(xBox);
        }
    }

    public static void main(String... args) throws InterruptedException{
        BlockingQueueTest aBlockingQueue = new BlockingQueueTest();
        aBlockingQueue.executer();
    }
}
