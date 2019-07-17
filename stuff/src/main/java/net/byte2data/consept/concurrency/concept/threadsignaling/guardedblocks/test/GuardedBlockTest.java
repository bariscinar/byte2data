package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test;

import net.byte2data.consept.concurrency.GetDetail;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.consumer.Consumer;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.producer.Producer;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.resource.Resource;

public class GuardedBlockTest {
    
    public void executer() throws InterruptedException{
        Resource resource = new Resource();

        Thread producer1 = new Thread(new Producer(resource));
        producer1.setName("PRODUCER-1");
        //Thread producer2 = new Thread(new Producer(resource));
        //producer2.setName("PRODUCER-2");
        //Thread producer3 = new Thread(new Producer(resource));
        //producer3.setName("PRODUCER-3");
        //Thread producer4 = new Thread(new Producer(resource));
        //producer4.setName("PRODUCER-4");

        Thread consumer1 = new Thread(new Consumer(resource));
        //Thread consumer2 = new Thread(new Consumer(resource));
        //Thread consumer3 = new Thread(new Consumer(resource));
        consumer1.setName("CONSUMER-1");
        //consumer2.setName("CONSUMER-2");
        //consumer3.setName("CONSUMER-3");

        //consumer2.start();
        //consumer3.start();
        //Thread.sleep(500);
        producer1.start();
        //producer2.start();
        //producer3.start();
        //producer4.start();
        //new Thread(new Mixer(resource)).start();
        consumer1.start();
    }

    public static void main(String... args) throws InterruptedException{
        GuardedBlockTest drop = new GuardedBlockTest();
        drop.executer();
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


}
