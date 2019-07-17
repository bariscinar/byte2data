package net.byte2data.consept.concurrency.concept.synchronization;

public class SynchronizationLoop {
    public static void getDetail(String message){
        System.out.format("%s/%s -> %s%n", Thread.currentThread().getId(), Thread.currentThread().getName(), message);
    }
    public static class Resource{
        private int var;
        public Resource(){
            var=0;
        }
        public synchronized void addVal(int val){
            getDetail("Call addVal");
            var+=val;
            getDetail("Called addVal");
        }
        public int getVal(){
            return this.var;
        }
    }
    public static class Executer implements Runnable{
        private Resource localResource;
        public Executer(Resource resource){
            this.localResource=resource;
        }
        public void run(){
            int localVariable=1;
            for(int index=1; index<=10; index++){
                localResource.addVal(index);
                getDetail(String.valueOf(localVariable++));
            }
        }
    }
    public static void main(String... args) throws InterruptedException{
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        Executer executer1 = new Executer(resource1);
        Executer executer2 = new Executer(resource2);

        //Tek bir executer'ı iki farklı thread ile çağırmak?
        /*
        Thread myTehread1 = new Thread(executer1);
        Thread myTehread2 = new Thread(executer2);
        myTehread1.start();
        myTehread2.start();
        */

        //ya da iki farklı executer'i iki farklı thread ile çağırmak?
        //nedir farkı?

        Thread myTehread3 = new Thread(executer1);
        Thread myTehread4 = new Thread(executer2);
        myTehread3.start();
        myTehread4.start();

        Thread.sleep(3000);

        getDetail(String.valueOf(resource1.getVal()));

    }
}
