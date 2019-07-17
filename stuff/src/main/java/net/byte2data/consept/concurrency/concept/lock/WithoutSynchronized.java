package net.byte2data.consept.concurrency.concept.lock;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;

public class WithoutSynchronized {
    private final class LockIncrease {
        //Initial state should be FALSE
        //I do not like this implementation, synchronized keyword is better...
        //By the way there is also synchronized keyword inside Lock object also...
        private volatile boolean lockState=false;
        synchronized void lock() throws InterruptedException{
            while (lockState){
                //GetDetail.getThreadDetails("LockIncrease-waiting...");
                wait();
                //GetDetail.getThreadDetails("LockIncrease-waiting completed!");
            }
            this.lockState=true;
        }
        synchronized void unLock(){
            //GetDetail.getThreadDetails("LockIncrease-notifiying...");
            this.lockState=false;
            notify();
            //GetDetail.getThreadDetails("LockIncrease-notifiying completed!");
        }
    }
    private final class LockDecrease {
        //Initial state should be FALSE
        //I do not like this implementation, synchronized keyword is better...
        //By the way there is also synchronized keyword inside Lock object also...
        private volatile boolean lockState=false;
        synchronized void lock() throws InterruptedException{
            while (lockState){
                //GetDetail.getThreadDetails("LockDecrease-waiting...");
                wait();
                //GetDetail.getThreadDetails("LockDecrease-waiting completed!");
            }
            this.lockState=true;
        }
        synchronized void unLock(){
            //GetDetail.getThreadDetails("LockDecrease-notifiying...");
            this.lockState=false;
            notify();
            //GetDetail.getThreadDetails("LockDecrease-notifiying completed!");
        }
    }
    public void executer()throws InterruptedException{
        Resource resource = new Resource(10);
        List<Decreaser> decreaserList = new ArrayList<>();
        List<Increaser> increaserList = new ArrayList<>();

        List<Thread> threadListForIncreaser = new ArrayList<>();
        List<Thread> threadListForDecreaser = new ArrayList<>();
        Increaser tempIncreaser;
        Decreaser tempDecreaser;
        for(int index=0; index<1000; index++){
            tempDecreaser=new Decreaser(resource);
            tempIncreaser=new Increaser(resource);
            increaserList.add(tempIncreaser);
            decreaserList.add(tempDecreaser);
            threadListForIncreaser.add(new Thread(tempIncreaser));
            threadListForDecreaser.add(new Thread(tempDecreaser));
        }
        for(int x=0; x<1000; x++){
            threadListForIncreaser.get(x).start();
            threadListForDecreaser.get(x).start();
        }
        Thread.sleep(2000);
        GetDetail.getThreadDetails(String.valueOf(resource.resourceValue));
    }
    public static void main(String... args) throws InterruptedException{
        WithoutSynchronized withSynchronized = new WithoutSynchronized();
        withSynchronized.executer();

    }

    private class Resource{
        private int resourceValue;
        private LockIncrease lockIncrease;
        private LockDecrease lockDecrease;
        Resource(int initialValue){
            this.resourceValue=initialValue;
            this.lockIncrease =new LockIncrease();
            this.lockDecrease=new LockDecrease();
        }
        void increament(){
            try{
                lockIncrease.lock();
                //int waitingPeriod = new Random().nextInt(1000);
                //GetDetail.getThreadDetails("increament/waitingPeriod: " + waitingPeriod);
                //Thread.sleep(waitingPeriod);
                resourceValue++;
                //GetDetail.getThreadDetails("increament - resourceValue:"+resourceValue);
                lockIncrease.unLock();
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
        }
        void decreament(){
            try{
                lockIncrease.lock();
                //lockDecrease.lock();
                //int waitingPeriod = new Random().nextInt(1000);
                //GetDetail.getThreadDetails("decreament/waitingPeriod: " + waitingPeriod);
                //Thread.sleep(waitingPeriod);
                resourceValue--;
                //GetDetail.getThreadDetails("decreament - resourceValue:"+resourceValue);
                //lockDecrease.unLock();
                lockIncrease.unLock();
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
        }
    }
    private class Increaser implements Runnable{
        private Resource resource;
        Increaser(Resource resource){
            this.resource=resource;
        }
        public void run(){
            resource.increament();
        }
    }
    private class Decreaser implements Runnable{
        private Resource resource;
        Decreaser(Resource resource){
            this.resource=resource;
        }
        public void run(){
            resource.decreament();
        }
    }
}
