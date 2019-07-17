package net.byte2data.consept.concurrency.jvmmemorymodel;

import java.util.Random;

public class MySharedObject {
    public final static MySharedObject sharedClassObject = new MySharedObject();
    public Integer instanceObjectVariable1 = new Integer(new Random().nextInt());
    public Integer instanceObjectVariable2 = new Integer(new Random().nextInt());
    public long instancePrimitiveVariable1 = new Random().nextLong();
    public long instancePrimitiveVariable2 = new Random().nextLong();
}
