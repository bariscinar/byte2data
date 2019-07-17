package net.byte2data.consept.concurrency.blockingqueue;

/*
    BlockingQueue is a resource - lets say it as concurrent container or concurrent collection -
    that is blocking any enqueue/insertion attempt if the resource is full and
    is also blocking any dequeue/subtraction attempt if the resource is empty.
    Blocking queue is a kind of ???BOUNDED SEMAPHORE???
    Bounding means container/collection has a limitation/size.
*/

import net.byte2data.consept.concurrency.GetDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class BlockingQueueDefinition {

    private class Resource<E>{

        private List<E> resourceQueue;
        private int queueLimit;
        Resource(int limit){
            resourceQueue=new LinkedList<>();
            this.queueLimit=limit;
        }

        //blocking insertion - put
        synchronized void put(E item){
            while(resourceQueue.size()==queueLimit){
                try{
                    GetDetail.getThreadDetails("Waiting in PUT - blocking...");
                    wait();
                    GetDetail.getThreadDetails("Waiting in PUT - blocking released");
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            GetDetail.getThreadDetails("PUT: "+item.toString());
            resourceQueue.add(item);
            /*
            Notice how notifyAll() is only called from put() and take() if the resource size is equal to the size bounds (0 or limit).
            If the resource size is not equal to either bound when enqueue() or dequeue() is called,
            there can be no threads waiting to either enqueue or dequeue items.
            */
            /*
            NOTE:
            My experiments proves that there should not be a condition here!
             */
            //if(this.resourceQueue.size()==0)
            notify();
        }

        //blocking subtraction - take
        synchronized E take(){
            while(resourceQueue.size()==0){
                try{
                    GetDetail.getThreadDetails("Waiting in TAKE - blocking...");
                    wait();
                    GetDetail.getThreadDetails("Waiting in TAKE - blocking released");
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            E x = resourceQueue.remove(0);
            GetDetail.getThreadDetails("TAKE: "+x.toString());
            /*
            Notice how notifyAll() is only called from enqueue() and dequeue() if the resource size is equal to the size bounds (0 or limit).
            If the resource size is not equal to either bound when enqueue() or dequeue() is called,
            there can be no threads waiting to either enqueue or dequeue items.
            */
            //if(this.resourceQueue.size()==queueLimit)
            notify();
            return x;
        }
    }

    private class Producer<E> implements Runnable{
        private Resource<E> sharedResource;
        private E item;
        Producer(Resource<E> rs, E item){
            this.sharedResource=rs;
            this.item=item;
        }
        public void run(){
            try{
                Thread.sleep((int)(Math.random()*100));
                sharedResource.put(item);
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
        }
    }

    private class Consumer<E> implements Runnable{
        private Resource<E> sharedResource;
        Consumer(Resource<E> rs){
            this.sharedResource=rs;
        }
        public void run(){
            try{
                Thread.sleep((int)(Math.random()*10000));
                sharedResource.take();
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }

        }
    }

    private void executer(){
        Resource<String> resource = new Resource<>(1);

        Producer<String> producer = new Producer<>(resource, String.valueOf(Math.random()));
        Consumer<String> consumer = new Consumer<>(resource);

        //Executor executorForConsumer = Executors.newCachedThreadPool();
        ExecutorService executorForConsumer = Executors.newFixedThreadPool(1);
        ExecutorService executorForProducer = Executors.newFixedThreadPool(1);

        /*
        Thread consumerThread;
        Thread producerThread;
        for(int index=0; index<10; index++){
            producerThread = new Thread(producer);
            producerThread.start();
            consumerThread = new Thread(consumer);
            consumerThread.start();
            producerThread = new Thread(producer);
            producerThread.start();
        }
        */
        int incrementValue=0;
        while(true){
            executorForProducer.execute(producer);
            executorForConsumer.execute(consumer);
            if(incrementValue++==2){
                try {
                    executorForProducer.shutdown();
                }catch (RejectedExecutionException ree){
                    //GetDetail.getThreadDetails(ree.pullMessage());
                    if(executorForProducer.isShutdown()){
                        GetDetail.getThreadDetails("1");
                    }
                }

            }

        }


    }

    public static void main(String... args){
        BlockingQueueDefinition blockingQueueSample = new BlockingQueueDefinition();
        blockingQueueSample.executer();
    }

}
