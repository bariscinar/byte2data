package net.byte2data.consept.inheritance.basics.nested.parent;

import net.byte2data.consept.inheritance.basics.nested.parent.Basics;
import net.byte2data.consept.inheritance.basics.box.parent.Box;
import net.byte2data.consept.inheritance.basics.box.sub.BoxWithWeight;

public class SubBasics extends Basics {
    public void doIt(){
        System.out.println(protectedClassInt);
    }

    public static void main(String... args){
        Box box = new Box();
        System.out.println(box.name);

        BoxWithWeight boxWithHeight = new BoxWithWeight();
        //System.out.println(boxWithHeight.weight);
        //System.out.println(boxWithHeight.depth);
        System.out.println(boxWithHeight.name);
    }

}
