package net.byte2data.consept.designpatterns.creational.singleton.eagerly.test;

import net.byte2data.consept.designpatterns.creational.singleton.eagerly.single.EagerlyUnique;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EagerlyUniqueTest {
    private static Set<EagerlyUnique> uniques = Collections.synchronizedSet(new HashSet<EagerlyUnique>());

    private static class UniqueCreater implements Runnable{
        @Override
        public void run() {
            EagerlyUnique unique = EagerlyUnique.getInstance();
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
