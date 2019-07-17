package net.byte2data.consept.inheritance.multilevel.shipment;

import net.byte2data.consept.inheritance.multilevel.box.Box;
import net.byte2data.consept.inheritance.multilevel.weightybox.WeightyBox;

public class Shipment extends WeightyBox {

    private double cost;


    public Shipment(Box anOtherBox){
        super(anOtherBox);
        System.out.println("Shipment - an other Box constructor");
    }
    public Shipment(WeightyBox anOtherWeightyBox){
        super(anOtherWeightyBox);
        System.out.println("Shipment - an other WeightyBox constructor");
    }
    public Shipment(Shipment anOtherShipment){
        super(anOtherShipment);
        this.cost=anOtherShipment.cost;
        System.out.println("Shipment - an other Shipment constructor");
    }
    public Shipment(double width, double height, double depth, double weight, double cost){
        super(width,height,depth,weight);
        this.cost = cost;
        System.out.println("Shipment - full constructor");
    }
    public Shipment(){
        super(1,2);
        System.out.println("Shipment - default constructor");
        this.cost=0;
    }
    public Shipment(double len, double weight, double cost){
        super(len,weight);
        this.cost=cost;
        System.out.println("Shipment - cube constructor");
    }
    public int volume(){
        double val = 0.0;
        //val = super.volume();
        System.out.println("Shipment - volume");
        return (int)val;
    }
    /*
    public final void doSmth(){
        System.out.println("Shipment - doSmth");
    }
    */

    public void doSmth(String formalParameter){
        System.out.println("Shipment - doSmth - formalParameter:"+formalParameter);
    }
}
