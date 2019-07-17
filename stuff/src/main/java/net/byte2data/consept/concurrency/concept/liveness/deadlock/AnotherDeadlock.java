package net.byte2data.consept.concurrency.concept.liveness.deadlock;

import net.byte2data.consept.concurrency.concept.liveness.deadlock.fairness.Lock;


public class AnotherDeadlock {

    public void executer(){
        AResource aResource = new AResource();
        BResource bResource = new BResource();
        WorkerA workerA = new WorkerA(aResource,bResource);
        WorkerB workerB = new WorkerB(aResource,bResource);
        Thread first = new Thread(workerA);
        Thread second = new Thread(workerB);
        first.start();
        second.start();
    }

    public static void main(String... args){
        AnotherDeadlock anotherDeadlock = new AnotherDeadlock();
        anotherDeadlock.executer();
    }

    public static void getDetail(String message){
        System.out.format("%s: %s%n",Thread.currentThread().getName(),message);
    }
    public class WorkerA implements Runnable{
        private AResource aResource;
        private BResource bResource;
        WorkerA(AResource a, BResource b){
            this.aResource=a;
            this.bResource=b;
        }
        public void run(){

            aResource.getAString();
            //bResource.getAString();

            //bResource.setAString(aResource.getAString());
            //aResource.setAString(bResource.getAString());
        }
    }
    public class WorkerB implements Runnable{
        private AResource aResource;
        private BResource bResource;
        WorkerB(AResource a, BResource b){
            this.aResource=a;
            this.bResource=b;
        }
        public void run(){

            aResource.setAString("A");
            //bResource.setAString("B");
            //aResource.getAString();

            //aResource.setAString(bResource.getAString());
        }
    }

    public class AResource{
        private volatile boolean empty;

        //Şu aşağıdaki adamı (private AResource aResource) hiç anlamamışımdır?
        //Yani o bir objenin kendisinin private olarak kendi içinde tutulma nedenini?
        //Var bunun güzel bir kullanım amacı ama ben bilmiyorum...
        private AResource aResource;

        private String aString;
        AResource(){
            this.empty=true;
            //this.aResource=new AResource();
            this.aString="";
        }
        synchronized void changeStatus(boolean state){
            this.empty=state;
        }
        synchronized boolean isEmpty(){
            return this.empty;
        }
        synchronized void setAString(String paramStr){
            try{
                while (!isEmpty()){
                    getDetail("AResource - waiting to put: " + paramStr);
                    wait();
                }
                changeStatus(false);
                getDetail("AResource - notifying to have put: " + paramStr);
                notify();
                this.aString=paramStr;
            }catch (InterruptedException ie){
                getDetail(ie.getMessage());
            }
        }
        synchronized String getAString(){
            try{
                while (isEmpty()){
                    getDetail("AResource - waiting to get...");
                    wait();
                }
                changeStatus(true);
                getDetail("AResource - notifying to have got: "+this.aString);
                notify();
                return this.aString;
            }catch (InterruptedException ie){
                getDetail(ie.getMessage());
            }
            return null;
        }

    }

    public class BResource{
        private volatile boolean empty;
        private Lock bLock;

        //Şu aşağıdaki adamı (private AResource aResource) hiç anlamamışımdır?
        //Yani o bir objenin kendisinin private olarak kendi içinde tutulma nedenini?
        //Var bunun güzel bir kullanım amacı ama ben bilmiyorum...
        private BResource bResource;

        private String bString;
        BResource(){
            this.empty=true;
            //this.bResource=new BResource();
            this.bString="";
            this.bLock=new Lock() ;

        }

        void changeStatus(boolean state){
            this.empty=state;
        }

        boolean isEmpty(){
            return this.empty;
        }

        void setAString(String paramStr){
            try{
                getDetail("BResource - waiting to put: " + paramStr);
                bLock.lock();
                getDetail("BResource - notifying to have put: " + paramStr);
                this.bString=paramStr;
                bLock.unlock();
            }catch (InterruptedException ie){
                getDetail(ie.getMessage());
            }
        }

        String getAString(){
            try{
                getDetail("BResource - waiting to get...");
                bLock.lock();
                getDetail("BResource - notifying to have got: " + this.bString);
                bLock.unlock();
                return this.bString;
            }catch (InterruptedException ie){
                getDetail(ie.getMessage());
            }
            return null;
        }
    }

}
