package net.byte2data.consept.concurrency.concept.synchronization.synchronizedmethods;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedStatement {
    private String name;
    private int counter;
    private List<String> stringArrayList;

    public SynchronizedStatement(){
        stringArrayList=new ArrayList<>();
    }

    public synchronized void addName(String name){
        this.name=name;
        counter++;
        stringArrayList.add(name);
    }

    public void addSurName(String name){
        synchronized (this){
            this.name=name;
            counter++;
        }
        stringArrayList.add(name);
    }
}
