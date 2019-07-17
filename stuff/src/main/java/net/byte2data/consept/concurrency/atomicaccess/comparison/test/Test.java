package net.byte2data.consept.concurrency.atomicaccess.comparison.test;

import net.byte2data.consept.concurrency.atomicaccess.comparison.resource.Resource;
import net.byte2data.consept.concurrency.atomicaccess.comparison.runner.Increasing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String... args) throws InterruptedException{
        Resource resource = new Resource();
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for(int index=0; index<100000; index ++)
            executorService.submit(new Increasing(resource));
        Thread.sleep(5000);
        System.out.println(resource.toString());
        System.out.println("done");
        System.exit(1);
    }

}
