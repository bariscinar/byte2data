package net.byte2data.consept.designpatterns.creational.singleton.doublechecked.test;

import net.byte2data.consept.designpatterns.creational.singleton.doublechecked.single.DoubleCheckedUnique;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DoubleCheckedUniqueTest {
    private volatile static Set<DoubleCheckedUnique> uniques = Collections.synchronizedSet(new HashSet<DoubleCheckedUnique>());

    private static class UniqueCreater implements Runnable{
        @Override
        public void run() {
            DoubleCheckedUnique unique = DoubleCheckedUnique.getInstance();
            uniques.add(unique);
        }
    }

    public static void main(String... args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for(int index=0; index<10000; index++)
            executorService.submit(new UniqueCreater());
        Thread.sleep(5000);
        System.out.println(uniques.size());
        System.exit(1);
    }

}
