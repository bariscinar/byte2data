package net.byte2data.consept.designpatterns.creational.singleton.lazily.test;

import net.byte2data.consept.designpatterns.creational.singleton.lazily.single.LazilyUnique;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LazilyUniqueTest {
    private static Set<LazilyUnique> uniques = Collections.synchronizedSet(new HashSet<>());

    private static class UniqueCreater implements Runnable{
        @Override
        public void run() {
            LazilyUnique unique = LazilyUnique.getInstance();
            uniques.add(unique);
        }
    }

    public static void main(String... args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for(int index=0; index<100000; index++)
            executorService.submit(new UniqueCreater());
        Thread.sleep(5000);
        System.out.println("there can be only " + uniques.size());
        System.exit(1);
    }

}
