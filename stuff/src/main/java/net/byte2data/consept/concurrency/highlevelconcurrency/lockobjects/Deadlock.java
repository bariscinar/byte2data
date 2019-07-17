package net.byte2data.consept.concurrency.highlevelconcurrency.lockobjects;

import java.util.Random;

public class Deadlock {
    public static void getDetails(String message){
        System.out.format("%s:%s%n",Thread.currentThread().getName()+"/"+Thread.currentThread().getId(),message);
    }
    public class Friend{
        private Friend friendInstance;
        private String friendName;
        public Friend(String name){
            this.friendName=name;
            this.friendInstance=this;
        }
        public synchronized void bow(Friend f){
            getDetails("-> " + f.friendName + " tarafından ben:" + this.friendName + " selamlandım");
            f.bowBack(this);
        }
        private synchronized void bowBack(Friend me){
            getDetails("ben:" + me.friendName + " geri selamladım -> " + this.friendName);
        }
    }
    public class Executer implements Runnable{
        private Friend f1;
        private Friend f2;
        public Executer(Friend f1, Friend f2){
            this.f1=f1;
            this.f2=f2;
        }
        public void run(){
            Random random = new Random();
            for (;;) {
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {}
                f1.bow(f2);
            }
        }
    }
    public static void main(String... args){
        Deadlock deadlock = new Deadlock();
        Friend ali = deadlock.new Friend("ali");
        Friend veli = deadlock.new Friend("veli");
        new Thread(deadlock.new Executer(ali,veli)).start();
        new Thread(deadlock.new Executer(veli,ali)).start();
        //ali.bow(veli);
        //veli.bow(ali);
    }
}
