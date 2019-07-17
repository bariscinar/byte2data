package net.byte2data.consept.concurrency.concept.threadobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SingleOne {

    private static void threadMessage(String message){
        System.out.format("%s: %s%n",Thread.currentThread().getName()+"/"+Thread.currentThread().getId(),message);
    }

    private static class Resource<E> {

        private E item;
        private List<E> itemArrayList;

        private boolean readyForExtraction;
        private static int currentIndex;

        public Resource() {

            readyForExtraction = false;
            itemArrayList = new ArrayList<>();
        }

        public synchronized void putIntoResource(E item) {
            itemArrayList.add(item);
            //readyForExtraction=true;
            //SingleOne.threadMessage("putIntoResource is notifying all");
            //notifyAll();
        }

        public synchronized E fetchFromResource() {
            item = itemArrayList.get(currentIndex++);
            //readyForExtraction=true;
            //SingleOne.threadMessage("putIntoResource is notifying all");
            //notifyAll();
            return item;
        }
    }
    private class Box{
        private int height, lenght, witdh;
        private String boxName;
        private Box boxItself;
        Random ran = new Random();
        public Box(){
            this.height=(ran.nextInt(10)+1);
            this.lenght=(ran.nextInt(10)+1);
            this.witdh=(ran.nextInt(10)+1);
            this.boxName="boxName"+(Math.random());
            boxItself=this;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "boxName='" + boxName + '\'' +
                    '}';
        }
    }

    private class Feeder implements Runnable{
        private Class<Box> newItem;
        private Resource<Box> resourceReference;
        private final Random random;

        public Feeder(Resource<Box> localResource, Class<Box> newClass){
            this.resourceReference=localResource;
            this.newItem=newClass;
            random=new Random();
        }

        private Box getNewInstance(){
            try{
                //return newItem.newInstance();
                return new Box();
            }catch (Exception ex) {
                SingleOne.threadMessage("Feeder.getNewInstance catches:" + ex);
                //ex.printStackTrace();
            }finally {
                //SingleOne.threadMessage("Feeder.getNewInstance performing finally operations");
            }
            return null;
        }

        @Override
        public void run() {
            try{
                //T item = getNewInstance();
                Box boxItem = getNewInstance();
                //int sleepingPeriod = random.nextInt(2);
                //SingleOne.threadMessage("Feeder.run is sleeping in " + sleepingPeriod + "(sec)");
                //Thread.sleep(sleepingPeriod*1000);
                resourceReference.putIntoResource(boxItem);
                for(int index=0; index<Integer.MAX_VALUE; index++){
                    SingleOne.threadMessage(String.valueOf(index));
                    if(Thread.interrupted())
                        throw new InterruptedException("it takes too long!");
                }
                SingleOne.threadMessage("Feeder.run is being added item:" + boxItem + " to the list");
            }catch (InterruptedException ie){
                SingleOne.threadMessage("Feeder.run catches InterruptedException:"+ie);
                SingleOne.threadMessage("Feeder.run isInterrupted="+Thread.currentThread().isInterrupted());
                //Thread.currentThread().interrupt();
                //SingleOne.threadMessage("Feeder.run interrupted="+Thread.interrupted());
                //SingleOne.threadMessage("Feeder.run interrupted="+Thread.interrupted());
                //SingleOne.threadMessage("Feeder.run interrupted="+Thread.interrupted());
                //ie.printStackTrace();
            }finally {
                //SingleOne.threadMessage("putIntoResource performing finally operations");
            }

        }
    }

    private class Extracter implements Runnable{
        private Resource<Box> resourceReference;
        private final Random random;
        public Extracter(Resource<Box> localResource){
            this.resourceReference=localResource;
            random=new Random();
        }

        @Override
        public void run() {
            try{
                int sleepingPeriod = random.nextInt(20);
                SingleOne.threadMessage("fetchFromResource is sleeping in " + sleepingPeriod + "(sec)");
                Thread.sleep(sleepingPeriod*1000);
                Box item = resourceReference.fetchFromResource();
                SingleOne.threadMessage("fetchFromResource is being fetched item:"+ item + " from the list");
            }catch (InterruptedException ie){
                SingleOne.threadMessage("fetchFromResource catches:"+ie);
                SingleOne.threadMessage("interrupted="+Thread.interrupted());
                //ie.printStackTrace();
            }finally {
                //SingleOne.threadMessage("fetchFromResource performing finally operations");
            }
        }
    }

    public void executer() throws InterruptedException{
        Resource<Box> boxResource = new Resource<>();
        Feeder boxFeeder = new Feeder(boxResource,Box.class);
        Extracter boxExtracter = new Extracter(boxResource);
        Thread singleFeederThread = new Thread(boxFeeder);
        Thread[] feederThreads = new Thread[1];
        long startTime=0;
        for(int index=0; index<feederThreads.length; index++){
            feederThreads[index]=new Thread(boxFeeder);
            //feederThreads[index]=singleFeederThread;
            startTime = System.currentTimeMillis();
            feederThreads[index].start();
            threadMessage("Waiting for Thread:#" + index);
            while (feederThreads[index].isAlive()){
                threadMessage("Joining to Thread:#" + index + " for 5 seconds");
                feederThreads[index].join(5000);
                if(feederThreads[index].isAlive()){
                    threadMessage("Tired of waiting for Thread:#" + index + " to be completed");
                    feederThreads[index].interrupt();
                    Thread.sleep(100);
                    //threadMessage("Re-Joining to Thread:#" + index);
                    //feederThreads[index].join();
                    //threadMessage("Re-Joined to Thread:#" + index);
                    if(feederThreads[index].isInterrupted())
                        threadMessage("Thread:#" + index + " is INTERRUPTED");
                }
            }
        }
        threadMessage("Finally!");
    }

    public static void main(String... args) throws InterruptedException{
        SingleOne sO = new SingleOne();
        sO.executer();

    }

}
