package net.byte2data.consept.concurrency.staticmethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private class Worker implements Runnable{
        private Integer instanceValue;
        private StringBuilder localStringBuilder;

        Worker(Integer instanceValue){
            this.instanceValue=instanceValue;
        }

        @Override
        public void run() {
            //System.out.println(StaticTest.getValue(instanceValue) + " for " +instanceValue);
            StaticTest.concat(new StringBuilder(""), new StringBuilder(String.valueOf(instanceValue)), new StringBuilder(String.valueOf(instanceValue)));

        }
    }

    public static void main(String... args){
        ExecutorService executors = Executors.newFixedThreadPool(10000);
        Test test = new Test();
        for(int index=0; index<10000; index++){
            executors.submit(test.new Worker(index));
        }
    }

}
