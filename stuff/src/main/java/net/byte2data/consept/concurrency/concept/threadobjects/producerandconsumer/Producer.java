package net.byte2data.consept.concurrency.concept.threadobjects.producerandconsumer;

public class Producer implements Runnable {
    private Resource<Box> boxResource;
    public Producer(Resource<Box> resource){
        this.boxResource=resource;
    }
    @Override
    public void run(){
        try {
            Thread.sleep((int)(Math.random()*1000));
            Box box;
                box=new Box(1,1,1);
                boxResource.put(box);
        }catch (Exception ex){
            System.out.println(Thread.currentThread().getName() + " - Exception:" + ex.getMessage());
        }

    }
}
