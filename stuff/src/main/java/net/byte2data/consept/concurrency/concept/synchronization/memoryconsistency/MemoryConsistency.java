package net.byte2data.consept.concurrency.concept.synchronization.memoryconsistency;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.Random;

public class MemoryConsistency {
    class Resource{
        private volatile int value;
        Resource(int initialValue){
            this.value=initialValue;
        }
        synchronized void  increaseValue(){
            value++;
            GetDetail.getThreadDetails(String.valueOf(value));
        }
        synchronized void displayValue(){
           // GetDetail.getThreadDetails(String.valueOf(value));
        }
    }
    class Increaser implements Runnable{
        private Resource rouserceReference;
        Increaser(Resource resource){
            this.rouserceReference=resource;
        }
        public void run(){
            try{
                Thread.sleep(new Random().nextInt(20));
                rouserceReference.increaseValue();
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
        }
    }
    class Presenter implements Runnable{
        private Resource rouserceReference;
        Presenter(Resource resource){
            this.rouserceReference=resource;
        }
        public void run(){
            rouserceReference.displayValue();
        }
    }

    public static void main(String... args){
        MemoryConsistency memoryConsistency = new MemoryConsistency();
        Resource resource = memoryConsistency.new Resource(0);
        Increaser[] increaser = new Increaser[100];
        Presenter[] presenter = new Presenter[100];
        for(int index=0; index<100; index++){
            try{
                increaser[index] = memoryConsistency.new Increaser(resource);
                presenter[index] = memoryConsistency.new Presenter(resource);
                Thread increaserThread = new Thread(increaser[index]);
                Thread presenterThread = new Thread(presenter[index]);
                increaserThread.start();
                //increaserThread.join();
                //Thread.sleep(100);
                presenterThread.start();
                //presenterThread.join();
                /*
                MemoryConsistency.Increaser increaser = memoryConsistency.new Increaser(resource);
                MemoryConsistency.Presenter presenter = memoryConsistency.new Presenter(resource);
                Thread increaserThread = new Thread(increaser);
                Thread presenterThread = new Thread(presenter);
                increaserThread.start();
                increaserThread.join();
                presenterThread.start();
                */
            //}catch (InterruptedException ie){
            }catch (Exception ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }

        }
    }
}
