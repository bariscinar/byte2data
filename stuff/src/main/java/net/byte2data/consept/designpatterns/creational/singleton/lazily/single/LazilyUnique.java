package net.byte2data.consept.designpatterns.creational.singleton.lazily.single;

public class LazilyUnique {

    private static LazilyUnique uniqueInstance = null;

    private LazilyUnique(){

    }

    public synchronized static LazilyUnique getInstance(){
        if(null==uniqueInstance)
            uniqueInstance = new LazilyUnique();
        return uniqueInstance;
    }

}
