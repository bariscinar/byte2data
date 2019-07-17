package net.byte2data.consept.designpatterns.creational.singleton.eagerly.single;

public class EagerlyUnique {

    private static EagerlyUnique uniqueInstance = new EagerlyUnique();

    private EagerlyUnique(){

    }

    public static EagerlyUnique getInstance(){
        //if(null==uniqueInstance)
        //    uniqueInstance = new Unique();
        return uniqueInstance;
    }

}
