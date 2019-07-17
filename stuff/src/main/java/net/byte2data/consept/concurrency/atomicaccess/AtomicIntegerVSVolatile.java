package net.byte2data.consept.concurrency.atomicaccess;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerVSVolatile {

    private class Resource {
        private volatile int instanceVolatileValue = 0;
        private int instanceValue = 0;
        private  AtomicInteger instanceAtomicValue = new AtomicInteger(0);
        void increment() throws InterruptedException{
            //Thread.sleep(new Random().nextInt(10));
            this.instanceAtomicValue.incrementAndGet();
            this.instanceVolatileValue++;
            this.instanceValue++;
        }
    }

    private class Worker implements Runnable{
        private Resource localResource;

        Worker(Resource resource){
            this.localResource=resource;
        }

        public void run(){
            try{
                localResource.increment();
                //Thread.sleep(new Random().nextInt(10));
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
        }
    }

    public static void main(String... args) throws InterruptedException {
        AtomicIntegerVSVolatile whatIsThread = new AtomicIntegerVSVolatile();
        Resource otherResource = whatIsThread.new Resource();
        /*
        for(int index=1; index<=100000; index++)
            new Thread(whatIsThread.new Worker(otherResource)).start();
        */
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for(int index=1; index<=1000000; index++)
            executorService.submit(whatIsThread.new Worker(otherResource));


        Thread.sleep(5000);

        GetDetail.getThreadDetails("instanceAtomicValue:" + String.valueOf(otherResource.instanceAtomicValue));
        GetDetail.getThreadDetails("instanceVolatileValue:" + String.valueOf(otherResource.instanceVolatileValue));
        GetDetail.getThreadDetails("instanceValue:" + String.valueOf(otherResource.instanceValue));
    }
}
