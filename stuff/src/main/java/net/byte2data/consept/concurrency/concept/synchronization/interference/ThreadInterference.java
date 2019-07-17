package net.byte2data.consept.concurrency.concept.synchronization.interference;

import net.byte2data.consept.concurrency.GetDetail;

public class ThreadInterference {

    public class Source{
        private int local;
        Source(){
            this.local=0;
        }
        synchronized int increase(){
            try{
                //Thread.sleep(new Random().nextInt(2000-((int)Thread.currentThread().getId()*10)));
                //Thread.sleep(new Random().nextInt(10));
                return local++;
           // }catch (InterruptedException ie){
            }catch (Exception ie){
                GetDetail.getThreadDetails(ie.getMessage());
            }
            return 0;

        }
        int decrease(){
            return local--;
        }
        int getLatestValue(){
            return local;
        }
    }

    public class ExecuterIncrease implements Runnable{
        private Source localSource;
        ExecuterIncrease(Source sourceParam){
            this.localSource=sourceParam;
        }
        public void run(){
            GetDetail.getThreadDetails("Increased:"+String.valueOf(localSource.increase()));
        }
    }

    public class ExecuterDecrease implements Runnable{
        private Source localSource;
        ExecuterDecrease(Source sourceParam){
            this.localSource=sourceParam;
        }
        public void run(){
            GetDetail.getThreadDetails(String.valueOf("Decreased:"+localSource.decrease()));
        }
    }

    public static void main(String... args){
        try{
            ThreadInterference threadInterference = new ThreadInterference();
            Source source = threadInterference.new Source();
            for(int index=0; index<100; index++){

                ExecuterIncrease executerIncrease = threadInterference.new ExecuterIncrease(source);
                ExecuterDecrease executerDecrease = threadInterference.new ExecuterDecrease(source);
                Thread localIncreaseThread = new Thread(executerIncrease);
                Thread localDecreaseThread = new Thread(executerDecrease);
                localIncreaseThread.start();
                localIncreaseThread.join();
                localDecreaseThread.start();
                localDecreaseThread.join();
            }
            Thread.sleep(3000);
            System.out.format("%s: Latest value -> %s%n",Thread.currentThread().getName(),source.getLatestValue());
        }catch (InterruptedException ie){
            System.out.format("%s: %s%n", Thread.currentThread().getName(),ie);
        }

    }

}
