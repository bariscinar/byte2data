package net.byte2data.consept.concurrency.concept.threadobjects.producerandconsumer;

public class Consumer implements Runnable{
    private Resource<Box> boxResource;
    public Consumer(Resource resource){
        this.boxResource=resource;
    }

    @Override
    public void run() {
        while(1==1){
            try{
                Thread.sleep((int)((Math.random()) * 1000));
                System.out.println(boxResource.get());
            }catch (Exception ex){
                System.out.println(Thread.currentThread().getName() + " - Exception:" + ex.getMessage());
            }

        }
    }
}
