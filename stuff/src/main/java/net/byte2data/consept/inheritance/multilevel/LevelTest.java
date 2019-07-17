package net.byte2data.consept.inheritance.multilevel;

import net.byte2data.consept.inheritance.multilevel.box.Box;
import net.byte2data.consept.inheritance.multilevel.shipment.Shipment;
import net.byte2data.consept.inheritance.multilevel.weightybox.WeightyBox;

public class LevelTest {

    public static void main(String... args){
        System.out.println("Instantiating Box");
        Box box = new Box(1);
        System.out.println("----");
        System.out.println("Instantiating WeightyBox");
        WeightyBox weightyBox = new WeightyBox(1,2);
        System.out.println("----");
        System.out.println("Instantiating Shipment");
        Shipment shipment = new Shipment();
        System.out.println("----");
        System.out.println("Calling box.doSmth()");
        box.doSmth();
        System.out.println("----");
        System.out.println("Calling Box.doSmth(formalParameter)");
        box.doSmth("box doSmth");
        System.out.println("----");
        System.out.println("Calling WeightyBox.doSmth(formalParameter)");
        weightyBox.doSmth("weighty doSmth");
        System.out.println("----");
        System.out.println("Calling Shipment.doSmth(formalParameter)");
        shipment.doSmth("shipment doSmth");
        System.out.println("----");
    }

}
