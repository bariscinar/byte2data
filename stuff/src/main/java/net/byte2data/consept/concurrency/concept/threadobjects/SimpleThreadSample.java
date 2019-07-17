package net.byte2data.consept.concurrency.concept.threadobjects;

public class SimpleThreadSample {
    private static long waitingPeriod = 1000*5;
    private static String[] writings = {"item1", "item2", "item3", "item4", "item5"};
    private static void threadMessage(String message){
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n",threadName,message);
    }
    private static class MessageLoop implements Runnable{
        @Override
        public void run() {
            try{
                for(String item : writings){
                    threadMessage(item);
                    Thread.sleep(1500);
                }
            }catch (InterruptedException ie){
                threadMessage("I wasn't done: " + ie);
            }
        }
    }
    public static void main(String... args){
        try{

            threadMessage("Starting MessageLoop Thread...");
            MessageLoop messageLoop = new MessageLoop();
            Thread writerThread = new Thread(messageLoop);
            long startTime = System.currentTimeMillis();
            writerThread.start();
            threadMessage("Waiting for MessageLoop Thread to be completed...");
            while(writerThread.isAlive()){
                threadMessage("Still waiting...");
                writerThread.join(2000);
                if(writerThread.isAlive() && (System.currentTimeMillis()-startTime > waitingPeriod)){
                    threadMessage("Tired of waiting!");
                    writerThread.interrupt();
                    //writerThread.join();
                    if(writerThread.isInterrupted()){
                        threadMessage("MessageLoop Thread Interrupted!");
                    }
                }
            }

            threadMessage("Finally!");

        }catch (InterruptedException ie){
            threadMessage("InterruptedException: " + ie);
        }

    }
}
