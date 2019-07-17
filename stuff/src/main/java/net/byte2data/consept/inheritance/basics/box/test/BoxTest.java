package net.byte2data.consept.inheritance.basics.box.test;


import net.byte2data.consept.inheritance.basics.box.parent.Box;
import net.byte2data.consept.inheritance.basics.box.sub.BoxWithWeight;

public class BoxTest {

    public static void main(String... args) {

        Box myBox = new Box(3,3,3,"myBox");
        //System.out.println("box.height - private member" + box.height);
        //System.out.println("box.width - package private member" + myBox.width);
        //System.out.println("box.depth - protected member" + myBox.depth);
        System.out.println("myBox.name - public member" + myBox.name);
        System.out.println("myBox.volume() - public member" + myBox.volume());

        BoxWithWeight myBoxWithHeight = new BoxWithWeight(5,5,5,"myBoxWithHeight",5);
        BoxWithWeight boxWithWeight = new BoxWithWeight(myBox);
        BoxWithWeight boxWithWeight2 = new BoxWithWeight(myBoxWithHeight);
        //System.out.println("myBoxWithHeight.height - private member" + myBoxWithHeight.height);
        //System.out.println("myBoxWithHeight.width - package private member" + myBoxWithHeight.width);
        //System.out.println("myBoxWithHeight.depth - protected member" + myBoxWithHeight.depth);
        System.out.println("myBoxWithHeight.weight - public member" + myBoxWithHeight.weight);
        System.out.println("myBoxWithHeight.name - public member" + myBoxWithHeight.name);
        System.out.println("myBoxWithHeight.volume() - public member" + myBoxWithHeight.volume());

        myBox = myBoxWithHeight;
        System.out.println("myBox.volume() - public member" + myBox.volume());
        myBoxWithHeight.setHeight(10);
        System.out.println("myBox.volume() - public member" + myBox.volume());

        System.out.println("myBox.weight - public member" + ((BoxWithWeight) myBox).weight);

        if(myBox.equals(myBoxWithHeight))
            System.out.println("equals");
        else
            System.out.println("not equals");

        if(myBox==myBoxWithHeight) {
            System.out.println("same");
            myBoxWithHeight= (BoxWithWeight) myBox;
            System.out.println("myBox.weight - public member" + ((BoxWithWeight) myBox).weight);
            System.out.println("myBoxWithHeight.weight - public member" + myBoxWithHeight.weight);
        }
        else
            System.out.println("not same");
        //myBoxWithHeight = myBox;


    }
}