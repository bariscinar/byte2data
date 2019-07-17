package net.byte2data.consept.concurrency.concept.threadobjects.producerandconsumer;

import java.util.ArrayList;

public class  Resource<J> {
    private static int index;
    private static int increaseIndex(){
        return index++;
    }
    private ArrayList<J> resource;
    public Resource(){
        resource=new ArrayList<>();
    }

    public void put(J item){
        resource.add(item);
    }

    public synchronized J get(){
        return resource.get(increaseIndex());
    }
}
