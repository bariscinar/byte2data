package net.byte2data.consept.concurrency.concept.synchronization.raceconditionincriticalsection;

import java.util.Random;

public class RaceConditionInCriticalSection {
    private void getDetail(String message){
        System.out.format("%s:%s%n",Thread.currentThread().getName(),message);
    }


    private class Resource{
        private final String resourceName;
        public Resource(String name){
            this.resourceName=name;
        }
        public String getName(){
            return this.resourceName;
        }
    }
    private class Executer implements Runnable{
        private Resource localResource;
        public Executer(Resource resource){
            this.localResource=resource;
        }
        public void run(){
            try{
                getDetail(localResource.getName());
                Thread.sleep(1);
            }catch (Exception ie){
                getDetail(ie.getMessage());
            }

        }
    }
    private class OtherResource{
        private volatile int instanceValue = 0;
        public void add(int val) throws InterruptedException{
            Thread.sleep(new Random().nextInt(10));
            this.instanceValue+=val;
        }
    }
    private class OtherExecuter implements Runnable{
        private OtherResource localResource;
        private int val;
        public OtherExecuter(OtherResource resource, int val){
            this.localResource=resource;
            this.val=val;
        }
        public void run(){
            try{
                localResource.add(val);
                Thread.sleep(1);
            }catch (Exception ie){
                getDetail(ie.getMessage());
            }

        }
    }

    public static void main(String... args) throws InterruptedException{
        RaceConditionInCriticalSection whatIsThread = new RaceConditionInCriticalSection();
        Resource resource1 = whatIsThread.new Resource("resource1");
        for(int index=0; index<10; index++)
            new Thread(whatIsThread.new Executer(resource1),""+index).start();


        OtherResource otherResource = whatIsThread.new OtherResource();
        for(int index=1; index<=1000; index++)
            new Thread(whatIsThread.new OtherExecuter(otherResource,1)).start();



        Thread.sleep(1000);
        whatIsThread.getDetail(String.valueOf(otherResource.instanceValue));
    }
}
