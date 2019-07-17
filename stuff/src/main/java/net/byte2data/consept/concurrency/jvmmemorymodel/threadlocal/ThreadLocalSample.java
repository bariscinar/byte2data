package net.byte2data.consept.concurrency.jvmmemorymodel.threadlocal;

import java.util.Random;

public class ThreadLocalSample {

    public static void getDetails(String message){
        System.out.format("%s:%s%n", Thread.currentThread().getName(),message);
    }

    private class Resource{
        private String variable;
        Resource(String var){
            this.variable=var;
        }
        void setValue(String newVal){
            getDetails("Resource.getValue is called with waiting period:"+newVal);
            try{
                Thread.sleep(Integer.parseInt(newVal));
                this.variable=newVal;
            }catch (InterruptedException ie){
                getDetails(ie.getMessage());
            }
        }
        String getValue(){
            return this.variable;
        }
    }

    private class Worker implements Runnable{
        private Resource localResource;
        private ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();
        private InheritableThreadLocal<Integer> integerInheritableThreadLocal = new InheritableThreadLocal<>();
        private int localVar;
        Worker(Resource rs){
            this.localResource=rs;
        }
        public void run(){
            ThreadLocal<Double> integerThreadLocal2 = new ThreadLocal<>();
            integerThreadLocal2.set((Math.random()));
            int localVar2;
            int waitingPeriod = new Random().nextInt(5000);
            integerThreadLocal.set((int)(Math.random()*1000D));
            integerInheritableThreadLocal.set((int)(Math.random()*1000D));
            localVar=(int)(Math.random()*1000D);
            localVar2=(int)(Math.random()*1000D);
            localResource.setValue(String.valueOf(waitingPeriod));
            getDetails("integerThreadLocal:"+String.valueOf(integerThreadLocal.get()));
            getDetails("integerThreadLocal2:"+String.valueOf(integerThreadLocal2.get()));
            getDetails("integerInheritableThreadLocal:"+String.valueOf(integerInheritableThreadLocal.get()));
            getDetails("localVar:"+String.valueOf(localVar));
            getDetails("localVar2:"+String.valueOf(localVar2));
        }
    }

    

    public void executer() throws InterruptedException{
        Resource rs = new Resource("resource-1");

        Worker worker1 = new Worker(rs);
        //Worker worker2 = new Worker(rs);
        //Worker worker3 = new Worker(rs);
        //Worker worker4 = new Worker(rs);

        Thread executer1 = new Thread(worker1);
        Thread executer2 = new Thread(worker1);
        Thread executer3 = new Thread(worker1);
        Thread executer4 = new Thread(worker1);

        executer1.start();
        executer2.start();
        executer3.start();
        executer4.start();

        /*
        getDetails("executer 1 joined");
        executer1.join();
        getDetails("executer 2 joined");
        executer2.join();
        getDetails("executer 3 joined");
        executer3.join();
        getDetails("executer 4 joined");
        executer4.join();
        */

        Thread.sleep(20000);
        getDetails("Get Value: " + rs.getValue());

    }

    public static void main(String... args)throws InterruptedException{
        ThreadLocalSample threadLocalSample = new ThreadLocalSample();
        threadLocalSample.executer();
        //Thread.sleep(1000);
        getDetails("Done:");
    }

}
