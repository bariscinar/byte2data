package net.byte2data.consept.concurrency.atomicaccess.comparison.resource;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Resource {

    //CLASS MEMBERS

    //MEMBER VARIABLES
    //Class Variables
    private static int classInt;
    private volatile static int volatileClassInt;
    private static AtomicInteger classAtomicInt;
    //Instance Variables
    private int instanceInt;
    private volatile int volatileInstanceInt;
    private AtomicInteger instanceAtomicInt = new AtomicInteger(0);

    //MEMBER METHODS
    //Class Methods
    public static synchronized void synchClassIncreament(){
        classInt++;
        volatileClassInt++;
    }
    public static void classIncreament(){
        classAtomicInt.incrementAndGet();
    }

    //Instance Methods
    public void nonSynchInstanceIncreament() throws InterruptedException{
        Thread.sleep(((long)Math.random()*10));
        instanceInt++;
    }
    public void nonSynchVolatileInstanceIncreament() throws InterruptedException{
        Thread.sleep(((long)Math.random()*10));
        volatileInstanceInt++;
    }
    public int instanceAtomicIncreament() throws InterruptedException{
        Thread.sleep(((long)Math.random()*10));
        return instanceAtomicInt.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Resource{" +
                "instanceInt=" + instanceInt +
                ", volatileInstanceInt=" + volatileInstanceInt +
                ", instanceAtomicInt=" + instanceAtomicInt +
                '}';
    }
}
