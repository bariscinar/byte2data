package net.byte2data.consept.concurrency.concept.threadobjects.producerandconsumer;

public class Executer {
    public static void main(String... args){
        Resource<Box> boxResource = new Resource<>();
        Producer producer = new Producer(boxResource);
        Consumer consumer = new Consumer(boxResource);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();


    }
}
