package net.byte2data.consept.concurrency.concept.threadobjects.introduction;

public class DrawSmth extends Thread {
    private String threadName;
    public DrawSmth(String name) {
        System.out.println("DrawSmth Constructor");
        this.threadName=name;
    }


    @Override
    public void run(){
        System.out.println("DrawSmth.run");
        Thread.currentThread().setName(threadName);
        try{
            for(int index=0; index<100; index++){
                if(index==200)
                    throw new CheckItOut("oops");
                System.out.println("DRAW threadname:"+Thread.currentThread().getName() +"/" + Thread.currentThread().getId());

            }
        }catch (CheckItOut ex){
            System.out.println(ex);
            ex.showIt();
        }

    }
}
