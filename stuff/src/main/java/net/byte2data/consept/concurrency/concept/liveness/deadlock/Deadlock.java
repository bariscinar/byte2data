package net.byte2data.consept.concurrency.concept.liveness.deadlock;

public class Deadlock {

    public static class Friend {
        private final String name;
        public Friend(String name){
            this.name=name;
        }
        public String getName(){
            return this.name;
        }
        public synchronized void bow(Friend bower){
            System.out.format("%s: %s" + " has bowed to me! %n", this.name, bower.getName());
            bower.bowBack(this);
        }
        public synchronized void bowBack(Friend bower){
            System.out.format("%s: %s" + " has bowed BACK to me! %n", this.name, bower.getName());
        }
    }

    public static class Runner implements Runnable {
        private Friend friend1;
        private Friend friend2;
        public Runner(Friend fr1, Friend fr2){
            this.friend1=fr1;
            this.friend2=fr2;
        }
        @Override
        public void run() {
            friend1.bow(friend2);
        }
    }

    public static void main(String... args) throws InterruptedException{
        Friend ali = new Friend("ALI");
        Friend veli = new Friend("VELI");
        Thread aliThread = new Thread(new Runner(ali,veli));
        Thread veliThread = new Thread(new Runner(veli,ali));
        aliThread.start();
        //Thread.sleep(2000);
        veliThread.start();
    }

}
