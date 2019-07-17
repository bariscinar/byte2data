package net.byte2data.consept.concurrency.concept.lock;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WithSynchronized {
    public void executer()throws InterruptedException{
        Resource resource = new Resource(10);
        List<Increaser> increaserList = new ArrayList<>();
        List<Decreaser> decreaserList = new ArrayList<>();
        List<Thread> threadListForIncreaser = new ArrayList<>();
        List<Thread> threadListForDecreaser = new ArrayList<>();
        Increaser tempIncreaser;
        Decreaser tempDecreaser;
        for(int index=0; index<100; index++){
            tempIncreaser=new Increaser(resource);
            tempDecreaser=new Decreaser(resource);
            increaserList.add(tempIncreaser);
            decreaserList.add(tempDecreaser);
            threadListForIncreaser.add(new Thread(tempIncreaser));
            threadListForDecreaser.add(new Thread(tempDecreaser));
        }
        for(int x=0; x<100; x++){
            threadListForIncreaser.get(x).start();
            threadListForDecreaser.get(x).start();
        }
    }
    public static void main(String... args) throws InterruptedException{
        WithSynchronized withSynchronized = new WithSynchronized();
        withSynchronized.executer();
    }

    private class Resource{
        private int resourceValue;
        Resource(int initialValue){
            this.resourceValue=initialValue;
        }
        synchronized void increament(){
            try{
                int waitingPeriod = new Random().nextInt(1000);
                //GetDetail.getThreadDetails("increament/waitingPeriod: " + waitingPeriod);

                resourceValue++;
                GetDetail.getThreadDetails("increament - resourceValue:"+resourceValue);
                Thread.sleep(5000);
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
        }
        synchronized void decreament(){
            try{
                int waitingPeriod = new Random().nextInt(1000);
                //GetDetail.getThreadDetails("decreament/waitingPeriod: " + waitingPeriod);
                Thread.sleep(waitingPeriod);
                resourceValue--;
                GetDetail.getThreadDetails("decreament - resourceValue:"+resourceValue);
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
