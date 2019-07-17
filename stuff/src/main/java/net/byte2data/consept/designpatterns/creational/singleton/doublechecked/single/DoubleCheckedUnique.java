package net.byte2data.consept.designpatterns.creational.singleton.doublechecked.single;

public class DoubleCheckedUnique {

    private volatile static DoubleCheckedUnique uniqueInstance = null;

    private DoubleCheckedUnique(){

    }

    public static DoubleCheckedUnique getInstance(){
        if(null==uniqueInstance){
            synchronized (DoubleCheckedUnique.class){
                if(null==uniqueInstance)
                    uniqueInstance=new DoubleCheckedUnique();
            }
        }
        return uniqueInstance;
    }

}
