package net.byte2data.consept.concurrency.blockingqueue.impl.arrayblockingqueue;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AnArrayBlockingQueue {

    private void executer() throws Exception{
        BlockingQueue integerBlockingQueue = new ArrayBlockingQueue(6);


        Producer producer = new Producer(integerBlockingQueue);
        Consumer consumer = new Consumer(integerBlockingQueue);

        Thread producerThread;
        Thread consumerThread;

        for(int index=0; index<10; index++){
            producerThread = new Thread(producer);
            consumerThread = new Thread(consumer);
            consumerThread.start();
            producerThread.start();
            /*
            Thread.sleep(15);
            GetDetail.getThreadDetails("Is Alive="+ producerThread.isAlive(),producerThread.getId(),producerThread.getName());
            GetDetail.getThreadDetails("Is Daemon="+ producerThread.isDaemon(),producerThread.getId(),producerThread.getName());
            GetDetail.getThreadDetails("State="+ producerThread.getState().name(),producerThread.getId(),producerThread.getName());
            */
            //Create more consumer
            //consumerThread = new Thread(consumer);
            //consumerThread.start();
        }


    }

    public static void main(String... args) throws Exception{
        AnArrayBlockingQueue anArrayBlockingQueue = new AnArrayBlockingQueue();
        anArrayBlockingQueue.executer();
    }


    private class Producer implements Runnable{
        private BlockingQueue blockingQueueResource;
        public Producer(BlockingQueue blockingQueue){
            this.blockingQueueResource=blockingQueue;
        }
        public void run(){
            int waitingPeriod=0;
            Object item;
            try{
                item=new Object();
                //waitingPeriod = new Random().nextInt(51);
                //Thread.sleep(waitingPeriod);
                GetDetail.getThreadDetails("Producing is STARTING for :"+item);

                /*
                put - blocks
                */
                blockingQueueResource.put(item);
                GetDetail.getThreadDetails("Producing is COMPLETED for :"+item);

                /*
                add - throws exception
                */
                //GetDetail.getThreadDetails( blockingQueueResource.add(item) ? "Producing is COMPLETED for :"+item: "Producing is FAILED for :"+item );

                /*
                offer - special value
                */
                //GetDetail.getThreadDetails( blockingQueueResource.offer(item) ? "Producing is COMPLETED for :"+item: "Producing is FAILED for :"+item );

                /*
                offer - time out
                */
                //GetDetail.getThreadDetails( blockingQueueResource.offer(item, 20, TimeUnit.SECONDS) ? "Producing is COMPLETED for :"+item: "Producing is FAILED for :"+item );


            }catch (Exception ie){
                GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to put");
            }
        }
    }

    private class Consumer implements Runnable{
        private BlockingQueue blockingQueueResource;
        public Consumer(BlockingQueue blockingQueue){
            this.blockingQueueResource=blockingQueue;
        }
        public void run(){
            int waitingPeriod = 0;
            Object item;
            try{
                //waitingPeriod = new Random().nextInt(51);
                //GetDetail.getThreadDetails("Consumer waiting:"+waitingPeriod + " msecs...");
                //Thread.sleep(waitingPeriod);
                GetDetail.getThreadDetails("Consuming is STARTING");
                /*
                take –
                waits for a head element of a resource and removes it.
                If the resource is empty, it blocks and waits for an element to become available
                */
                //item=blockingQueueResource.take();
                //GetDetail.getThreadDetails(item==null?"Consuming is FAILED": "Consuming is COMPLETED for :"+item );

                /*
                remove – exception
                */
                //item=blockingQueueResource.remove();
                //GetDetail.getThreadDetails(item==null?"Consuming is FAILED": "Consuming is COMPLETED for :"+item );

                /*
                poll(long timeout, TimeUnit unit) –
                retrieves and removes the head of the resource, waiting up to the specified wait
                time if necessary for an element to become available.
                Returns null after a timeout
                */
                //item=blockingQueueResource.poll();
                item=blockingQueueResource.poll(1,TimeUnit.MILLISECONDS);
                GetDetail.getThreadDetails(item==null?"Consuming is FAILED": "Consuming is COMPLETED for :"+item );



            }catch (Exception ie){
                GetDetail.getThreadDetails("Exception:" + ie.getMessage() + " occurred while waiting to get");
            }
        }
    }
}
