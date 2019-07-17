package net.byte2data.consept.concurrency.blockingqueue;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BlockingQueueDefinitionSample {

    public void executer(){

        QueueResource queueResource = new QueueResource(20);

        Consumer consumerWorker = new Consumer(queueResource,30);
        Producer producerWorker = new Producer(queueResource, 40);
        
        Thread a = new Thread(consumerWorker);
        Thread b = new Thread(producerWorker);
        b.start();
        a.start();

    }

    public static void main(String... args){
        BlockingQueueDefinitionSample blockingQueue = new BlockingQueueDefinitionSample();
        blockingQueue.executer();
    }


    public class Box{
        private int dimention;
        Box(int size){
            dimention=size;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "dimention=" + dimention +
                    '}';
        }
    }

    private class QueueResource{
        private int maxQueueSize;
        private List<Box> boxList;

        private boolean canPut(){
            return (boxList.size()<maxQueueSize);
        }
        private boolean canNotifyAllAfterPut(){
            return (boxList.size()==0);
        }

        private boolean canGet(){
            return (boxList.size()>0);
        }
        private boolean canNotifyAllAfterGet(){
            return (boxList.size()==maxQueueSize);
        }

        QueueResource(int queueSize){
            this.maxQueueSize=queueSize;
            boxList = new LinkedList<>();
        }

        synchronized void enqueue(Box box){
            while(!canPut()){
                try{
                    GetDetail.getThreadDetails("enqueue - waiting for box:"+box.toString());
                    //Thread.sleep(10000);
                    wait();
                    GetDetail.getThreadDetails("enqueue - waiting COMPLETED for box:"+box.toString());
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            boxList.add(box);
            GetDetail.getThreadDetails("enqueue - DONE for box:"+box.toString());
            /*
            Notice how notifyAll() is only called from enqueue() and dequeue() if the resource size is equal to the size bounds (0 or limit).
            If the resource size is not equal to either bound when enqueue() or dequeue() is called,
            there can be no threads waiting to either enqueue or dequeue items.
            */
            /*
            NOTE:
            My experiments proves that there should not be a condition here!
             */
            //if (canNotifyAllAfterPut()){
                GetDetail.getThreadDetails("enqueue - NOTIFIYALL");
                notify();
            //}else{
            //    GetDetail.getThreadDetails("enqueue - NOT NOTIFIYALL");
            //}

        }

        synchronized Box dequeue(){
            while(!canGet()){
                try{
                    GetDetail.getThreadDetails("dequeue - waiting");
                    wait();
                    GetDetail.getThreadDetails("dequeue - waiting COMPLETED");
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            GetDetail.getThreadDetails("dequeue - DONE for box:"+boxList.get(0).toString());
            if(canNotifyAllAfterGet()) {
                GetDetail.getThreadDetails("dequeue - NOTIFIYALL");
                notify();
            }else{
                GetDetail.getThreadDetails("dequeue - NOT NOTIFIYALL");
            }
            return boxList.remove(0);
        }

    }

    private class Producer implements Runnable{
        private QueueResource queueResource;
        private int boxSize;
        Producer(QueueResource qR, int max){
            this.queueResource=qR;
            this.boxSize=max;
        }
        public void run(){
            for(int index=0; index<boxSize; index++){
                try{
                    queueResource.enqueue(new Box(index));
                    Thread.sleep(new Random().nextInt(100));
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }

            }
        }
    }

    private class Consumer implements Runnable{
        private QueueResource queueResource;
        private int boxSize;
        Consumer(QueueResource qR, int max){
            this.queueResource=qR;
            this.boxSize=max;
        }
        public void run(){
            for(int index=0; index<boxSize; index++){
                try{
                    queueResource.dequeue();
                    Thread.sleep(new Random().nextInt(100));
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }

        }
    }
}