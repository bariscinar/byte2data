package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.Random;

public class GuardedBlockWithExternalLockObjectSample {

    private class LockObject{
        private volatile boolean lockState;
        LockObject(){
            this.lockState =false;
        }
        private synchronized void lock(){
            while (this.lockState){
                try{
                    wait();
                }catch (InterruptedException ie){
                    GetDetail.getThreadDetails(ie.getMessage());
                }
            }
            this.lockState =true;
        }
        private synchronized void unlock(){
            notify();
            this.lockState =false;
        }

    }
    
    public void executer() throws InterruptedException{
        Resource resource = new Resource();
        Thread producer1 = new Thread(new Producer(resource));
        producer1.setName("PRODUCER--1");
        Thread producer2 = new Thread(new Producer(resource));
        producer2.setName("PRODUCER--2");
        Thread producer3 = new Thread(new Producer(resource));
        producer3.setName("PRODUCER--3");
        Thread producer4 = new Thread(new Producer(resource));
        producer4.setName("PRODUCER--4");

        Thread consumer1 = new Thread(new Consumer(resource));
        Thread consumer2 = new Thread(new Consumer(resource));
        Thread consumer3 = new Thread(new Consumer(resource));
        consumer1.setName("CONSUMER--1");
        consumer2.setName("CONSUMER--2");
        consumer3.setName("CONSUMER--3");
        consumer1.start();
        consumer2.start();
        consumer3.start();
        Thread.sleep(500);
        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        //new Thread(new Mixer(resource)).start();
    }

    public static void main(String... args) throws InterruptedException{
        GuardedBlockWithExternalLockObjectSample drop = new GuardedBlockWithExternalLockObjectSample();
        drop.executer();
    }

    public class Producer implements Runnable{
        private Resource resource;
        public Producer(Resource rs){
            this.resource=rs;
        }
        private String getAStatement(){
            return String.valueOf(new Random().nextInt(99));
        }
        public void run(){

            //for(int index=0; index<100; index++){
            GetDetail.getThreadDetails("Producer.run is being starting");
                resource.putMessage(getAStatement());
            GetDetail.getThreadDetails("Producer.run has completed");
            //}
            //GetDetail.getThreadDetails("Producer.run is being starting");
            //resource.pushMessage("100");
            //GetDetail.getThreadDetails("Producer.run has completed");

        }
    }

    public class Consumer implements Runnable{
        private Resource resource;
        private String consumedMessage;
        public Consumer(Resource rs){
            this.resource=rs;
        }
        public void run(){
            //do{
            GetDetail.getThreadDetails("Consumer.run is being starting");
            consumedMessage =resource.getMessage();
            GetDetail.getThreadDetails("Consumer.run has completed");
            //}while(!consumedMessage.equalsIgnoreCase("100"));
        }
    }

    public class Mixer implements Runnable{
        private Resource resource;
        public Mixer(Resource rs){
            this.resource=rs;
        }
        public void run(){
            GetDetail.getThreadDetails("Mixer.run is being starting");
            resource.justMix();
            GetDetail.getThreadDetails("Mixer.run has completed");
        }
    }

    public class Resource{
        private String message;
        private LockObject lockObject;

        public Resource(){
            lockObject=new LockObject();
        }
         void putMessage(String message){
            try{
               lockObject.lock();
                this.message=message;
                GetDetail.getThreadDetails("pushMessage - "+message);
                lockObject.unlock();
            }catch (Exception ie){
                GetDetail.getThreadDetails("InterruptedException occurred in pushMessage: " + ie.getMessage());
            }

        }

         synchronized String getMessage(){
            try{
                lockObject.lock();
                GetDetail.getThreadDetails("pullMessage - "+message);
                lockObject.unlock();
            }catch (Exception ie){
                GetDetail.getThreadDetails("InterruptedException occurred in pullMessage: " + ie.getMessage());
            }
            return message;
        }

        public synchronized void justMix(){
            try{
                GetDetail.getThreadDetails("justMix is waiting to mix");
                Thread.sleep(2000);
                GetDetail.getThreadDetails("justMix is notifying to other threads");
                notify();
                GetDetail.getThreadDetails("justMix has notified to other threads");
            }catch (InterruptedException ie){
                GetDetail.getThreadDetails("InterruptedException occurred in pullMessage: " + ie.getMessage());
            }
        }
    }
}
