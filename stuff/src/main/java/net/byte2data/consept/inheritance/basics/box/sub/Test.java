package net.byte2data.consept.inheritance.basics.box.sub;

import net.byte2data.consept.inheritance.basics.box.parent.Box;

public class Test {

    public static void main(String... args){
        Box box = new Box();

        System.out.println("farklı paketteyiz...");
        //System.out.println("box.height - private member" + box.height);
        //System.out.println("box.width - package private member" + box.width);
        //System.out.println("box.depth - protected member" + box.depth);
        System.out.println("box.name - public member" + box.name);

        System.out.println("aynı paketteyiz...");
        BoxWithWeight boxWithHeight = new BoxWithWeight();
        //System.out.println("boxWithHeight.height - private member" + boxWithHeight.height);
        //System.out.println("boxWithHeight.width - package private member" + boxWithHeight.width);
        //System.out.println("boxWithHeight.depth - protected member" + boxWithHeight.depth);
        System.out.println("boxWithHeight.name - public member" + boxWithHeight.name);
        //System.out.println("boxWithHeight.weight - private member" + boxWithHeight.weight);
    }

}
