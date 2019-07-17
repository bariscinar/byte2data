package net.byte2data.consept.concurrency.blockingqueue.impl.linkedblockingqueue;


import net.byte2data.consept.concurrency.GetDetail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class BlockingQueueTest {

    public static void main(String... args){
        BlockingQueueTest queueSample = new BlockingQueueTest();
        queueSample.executer();
    }

    public void executer(){
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;

        GetDetail.getThreadDetails("CONSUMERS Thread Count:"+ N_CONSUMERS + " - Poison Pill Per Producer:" + poisonPillPerProducer);

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);

        for (int i = 0; i < N_PRODUCERS; i++) {
            new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer,i+1),"PRODUCER-"+(i+1)).start();

        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new NumbersConsumer(queue, poisonPill),"CONSUMER-"+(j+1)).start();
        }
    }

    private class NumbersProducer implements Runnable {
        private BlockingQueue<Integer> numbersQueue;
        private final int poisonPill;
        private final int poisonPillPerProducer;
        private final int basement;
        private final int id;

        public NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill, int poisonPillPerProducer, int threadId) {
            this.numbersQueue = numbersQueue;
            this.poisonPill = poisonPill;
            this.poisonPillPerProducer = poisonPillPerProducer;
            this.basement = 100;
            this.id=threadId;
        }
        public void run() {
            try {
                generateNumbers();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void generateNumbers() throws InterruptedException {
            int value, poison;
            for (int i = 0; i < 100; i++) {
                value=ThreadLocalRandom.current().nextInt((id)*basement,(id+1)*basement);
                GetDetail.getThreadDetails("PRODUCING: " + value);
                numbersQueue.put(value);
            }
            //for (int j = 0; j < poisonPillPerProducer; j++) {
                GetDetail.getThreadDetails("PRODUCING POISON: " + poisonPill);
                numbersQueue.put(poisonPill);
            //}
        }
    }



    public class NumbersConsumer implements Runnable {
        private BlockingQueue<Integer> queue;
        private final int poisonPill;

        public NumbersConsumer(BlockingQueue<Integer> queue, int poisonPill) {
            this.queue = queue;
            this.poisonPill = poisonPill;
        }
        public void run() {
            try {
                while (true) {
                    Integer number = queue.take();
                    if (number.equals(poisonPill)) {
                        GetDetail.getThreadDetails("POISON Message:"+poisonPill + " - Thread is OVER");
                        return;
                    }
                    GetDetail.getThreadDetails("CONSUMING: " + number);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
