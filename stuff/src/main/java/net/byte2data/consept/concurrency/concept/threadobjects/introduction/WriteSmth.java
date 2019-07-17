package net.byte2data.consept.concurrency.concept.threadobjects.introduction;

public class WriteSmth implements Runnable {

    private String threadName;
    private Thread waitingParty;

    public WriteSmth(String name, Thread waitingThread){
        System.out.println("WriteSmth Constructor");
        this.threadName=name;
        this.waitingParty=waitingThread;

    }


    @Override
    public void run() {
        try{
            Thread.currentThread().setName(threadName);
            System.out.println(waitingParty.getName() + " - joining");
            //waitingParty.start();
            waitingParty.join();
            System.out.println(waitingParty.getName() + " - joined");
            System.out.println("WriteSmth.run");
            for(int index=0; index<100; index++){
                System.out.println("WRITE threadname:"+Thread.currentThread().getName() +"/" + Thread.currentThread().getId());
            }
        }catch (Exception ex){
            System.out.println(ex);
        }

    }
}
