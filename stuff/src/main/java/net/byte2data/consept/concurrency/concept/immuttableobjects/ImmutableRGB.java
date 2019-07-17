package net.byte2data.consept.concurrency.concept.immuttableobjects;

public final class ImmutableRGB {

    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
     private String name;

    private void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
                || green < 0 || green > 255
                || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutableRGB(int red,
                        int green,
                        int blue,
                        String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
        return name;
    }

    public ImmutableRGB invert() {
        return new ImmutableRGB(255 - red,
                255 - green,
                255 - blue,
                "Inverse of " + name);
    }

    public static class ARunner implements Runnable{
        private ImmutableRGB color;
        public ARunner(ImmutableRGB synchronizedRGB){
            this.color=synchronizedRGB;
        }
        public void run(){
            System.out.println("set starting for A");
            color.setName("A pitch black");
            System.out.println("set completed for A");
        }
    }

    public static class BRunner implements Runnable{
        private ImmutableRGB color;
        public BRunner(ImmutableRGB synchronizedRGB){
            this.color=synchronizedRGB;
        }
        public void run(){
            System.out.println("set starting for B");
            color.setName("B pitch black");
            System.out.println("set completed for B");
        }
    }

    public static void main(String... args) throws InterruptedException{
        ImmutableRGB synchronizedRGB = new ImmutableRGB(0,0,0,"totally white");

        new Thread(new BRunner(synchronizedRGB)).start();
        int mycolor = synchronizedRGB.getRGB();
        new Thread(new ARunner(synchronizedRGB)).start();
        String name = synchronizedRGB.getName();
        System.out.format("%s:%s%n",name,mycolor);





    }

}
