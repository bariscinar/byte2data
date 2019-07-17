package net.byte2data.consept.inheritance.basics.box.parent;

import net.byte2data.consept.inheritance.basics.box.sub.BoxWithWeight;

public class Box {

    private double height;
    double width;
    protected double depth;
    public String name;

    public Box(){
        this.depth=-1.0;
        this.height=-1.0;
        this.width=-1.0;
        this.name="empty";
    }

    public Box(double h, double w, double d, String name){
        this.height=h;
        this.width=w;
        this.depth=d;
        this.name=name;
    }

    public Box(Box anOther){
        this.depth=anOther.depth;
        this.width=anOther.width;
        this.height=anOther.height;
        this.name=anOther.name;
    }

    public double volume(){
        return this.height*this.width*this.depth;
    }

    public void setHeight(double he){
        this.height=he;
    }

    public static void main(String... args){
        Box box = new Box();

        System.out.println("aynı classtayız...");
        System.out.println("box.height - private member" + box.height);
        System.out.println("box.width - package private member" + box.width);
        System.out.println("box.depth - protected member" + box.depth);
        System.out.println("box.name - public member" + box.name);

        System.out.println("farklı paketteyiz...");
        BoxWithWeight boxWithHeight = new BoxWithWeight();
        //System.out.println("boxWithHeight.height - private member" + boxWithHeight.height);
        //System.out.println("boxWithHeight.width - package private member" + boxWithHeight.width);
        System.out.println("boxWithHeight.depth - protected member" + boxWithHeight.depth);
        System.out.println("boxWithHeight.name - public member" + boxWithHeight.name);
        //System.out.println("boxWithHeight.weight - private member" + boxWithHeight.weight);
    }
}