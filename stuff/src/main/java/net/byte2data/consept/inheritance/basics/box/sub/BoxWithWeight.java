package net.byte2data.consept.inheritance.basics.box.sub;
import net.byte2data.consept.inheritance.basics.box.parent.Box;

public class BoxWithWeight extends Box {

    public double weight;

    public BoxWithWeight(){
        super();
        this.weight=-1;
    }

    public BoxWithWeight(double h, double w, double d, String name, double we){
        super(h,w,d, name);
        this.weight=we;
    }

    public BoxWithWeight(BoxWithWeight otherBoxWithHeight){
        super(otherBoxWithHeight);
        this.weight=otherBoxWithHeight.weight;
    }

    public BoxWithWeight(Box otherBox){
        super(otherBox);
        //this.weight=otherBoxWithHeight.weight;
    }

    public void displayBoxInfo(){
        //System.out.println("this.height: " +this.height);
        //System.out.println("this.width: " +this.width);
        System.out.println("this.depth: " +this.depth);
        System.out.println("this.weight: " +this.weight);
        System.out.println("this.name: " +this.name);
        System.out.println("this.volume: " +this.volume());

    }

    public static void main(String... args){
        Box box = new Box();

        System.out.println("farklı paketteyiz...");
        //System.out.println("box.height - private member" + box.height);
        //System.out.println("box.width - package private member" + box.width);
        //System.out.println("box.depth - protected member" + box.depth);
        System.out.println("box.name - public member" + box.name);

        System.out.println("aynı classtayız...");
        BoxWithWeight boxWithHeight = new BoxWithWeight();
        //System.out.println("boxWithHeight.height - private member" + boxWithHeight.height);
        //System.out.println("boxWithHeight.width - package private member" + boxWithHeight.width);
        System.out.println("boxWithHeight.depth - protected member" + boxWithHeight.depth);
        System.out.println("boxWithHeight.name - public member" + boxWithHeight.name);
        System.out.println("boxWithHeight.weight - private member" + boxWithHeight.weight);
    }

}
