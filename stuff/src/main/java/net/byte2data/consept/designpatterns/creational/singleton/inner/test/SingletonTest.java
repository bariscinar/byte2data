package net.byte2data.consept.designpatterns.creational.singleton.inner.test;

import net.byte2data.consept.designpatterns.creational.singleton.inner.single.TheOnlyOne;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonTest {

    private static Set classSet = Collections.synchronizedSet(new HashSet<>());

    private static class Runner implements Runnable{
        @Override
        public void run() {
            try{
                //Thread.sleep((long)(10 * Math.random()));
                TheOnlyOne instance = TheOnlyOne.getInstance();
                classSet.add(instance);
            }catch (Exception ex){
                System.out.println("exception: " + ex);
            }

        }
    }

    public static void main(String... args) throws InterruptedException{

        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for(int index=0; index<1000000; index++)
            executorService.submit(new Runner());

        Thread.sleep(10000);
        System.out.println(classSet.size());
        System.exit(1);

    }

}
