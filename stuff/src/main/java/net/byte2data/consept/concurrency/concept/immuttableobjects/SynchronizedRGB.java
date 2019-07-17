package net.byte2data.consept.concurrency.concept.immuttableobjects;

public class SynchronizedRGB {

    private int red;
    private int green;
    private int blue;
    private String name;

    private boolean test(int param){
        return (0<=param && param<=255);
    }

    private void check() throws IllegalArgumentException{
        if(!(test(this.blue)&&test(this.green)&&test(this.red)))
            throw new IllegalArgumentException();
    }

    public SynchronizedRGB(int x, int y, int z, String name){
        this.red=x;
        this.green=y;
        this.blue=z;
        this.name=name;
        check();
    }

    public void set(int red,int green,int blue,String name) {
        this.name = name;
         this.red=red;
        this.green=green;
        this.blue=blue;
    }

    public synchronized int getRGB(){
        return ((red << 16)|(green<<8)|blue);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }



    public static class ARunner implements Runnable{
        private SynchronizedRGB color;
        public ARunner(SynchronizedRGB synchronizedRGB){
            this.color=synchronizedRGB;
        }
        public void run(){
            //System.out.println("set starting for A");
            //for(int index=0;index<100;index++)
                color.setName("A pitch black"+Math.random());
            //System.out.println("set completed for A");
        }
    }

    public static class BRunner implements Runnable{
        private SynchronizedRGB color;
        public BRunner(SynchronizedRGB synchronizedRGB){
            this.color=synchronizedRGB;
        }
        public void run(){
            //System.out.println("set starting for B");
            //for(int index=0;index<100;index++)
                color.setName("B pitch black"+Math.random());
            //System.out.println("set completed for B");
        }
    }

    public static void main(String... args) throws InterruptedException{
        SynchronizedRGB synchronizedRGB = new SynchronizedRGB(0,0,0,"totally white");

        new Thread(new BRunner(synchronizedRGB)).start();
        new Thread(new ARunner(synchronizedRGB)).start();
        Thread.sleep(2);
        int mycolor = synchronizedRGB.getRGB();
        String name = synchronizedRGB.getName();



        System.out.format("%s:%s%n",name,mycolor);





    }
}
