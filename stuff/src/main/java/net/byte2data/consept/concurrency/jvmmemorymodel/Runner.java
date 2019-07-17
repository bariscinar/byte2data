package net.byte2data.consept.concurrency.jvmmemorymodel;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.Random;

public class Runner implements Runnable {
    private MySharedObject locaLSharedObject;

    Runner(MySharedObject mySharedObject){
        this.locaLSharedObject=mySharedObject;
        int localVariable0 = new Random().nextInt();
        GetDetail.getThreadDetails("localVariable0:"+localVariable0);
    }

    public void run(){
        method1();
        method2();
    }

    public void method1(){
        MySharedObject localVariable1 = MySharedObject.sharedClassObject;
        //MySharedObject localVariable1 = new MySharedObject();
        GetDetail.getThreadDetails("localVariable1.instanceObjectVariable1:"+localVariable1.instanceObjectVariable1);
        GetDetail.getThreadDetails("localVariable1.instanceObjectVariable2:"+localVariable1.instanceObjectVariable2);
        GetDetail.getThreadDetails("localVariable1.instancePrimitiveVariable1:"+localVariable1.instancePrimitiveVariable1);
        GetDetail.getThreadDetails("localVariable1.instancePrimitiveVariable2:"+localVariable1.instancePrimitiveVariable2);
        int localVariable2 =  new Random().nextInt();
        GetDetail.getThreadDetails("localVariable2:"+localVariable2);
    }

    public void method2(){
        int localVariable3 = new Random().nextInt();

        GetDetail.getThreadDetails("locaLSharedObject.instanceObjectVariable1:"+locaLSharedObject.instanceObjectVariable1);
        GetDetail.getThreadDetails("locaLSharedObject.instanceObjectVariable2:"+locaLSharedObject.instanceObjectVariable2);
        GetDetail.getThreadDetails("locaLSharedObject.instancePrimitiveVariable1:"+locaLSharedObject.instancePrimitiveVariable1);


        GetDetail.getThreadDetails("localVariable3:"+localVariable3);
    }
}
