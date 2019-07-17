package net.byte2data.consept.inheritance.multilevel.weightybox;

import net.byte2data.consept.inheritance.multilevel.box.Box;

public class WeightyBox extends Box {

    private double weight;


    public WeightyBox(Box anOtherBox){
        super(7);
        System.out.println("WeightyBox - an other Box constructor");
    }
    public WeightyBox(WeightyBox anOtherWeightyBox){
        super(6);
        this.weight=anOtherWeightyBox.weight;
        System.out.println("WeightyBox - an other WeightyBox constructor");
    }
    public WeightyBox(double width, double height, double depth, double weight){
        super(width);
        this.weight = weight;
        System.out.println("WeightyBox - full constructor");
    }

    public WeightyBox(double len, double weight){
        super(len);
        this.weight=weight;
        System.out.println("WeightyBox - cube constructor");
    }
    public int volume(){
        double val = 0.0;
        //val = super.volume();
        System.out.println("WeightyBox - volume");
        return (int) val;
    }
    /*
    public final void doSmth(){
        System.out.println("WeightyBox - doSmth");
    }
    */

    public void doSmth(String formalParameter){
        System.out.println("WeightyBox - doSmth - formalParameter:"+formalParameter);
    }


}
