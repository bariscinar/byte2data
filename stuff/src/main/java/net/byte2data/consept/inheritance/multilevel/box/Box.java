package net.byte2data.consept.inheritance.multilevel.box;

public class Box {

    private double width, height, depth;

    /*
    public Box(Box anOtherBox){
        System.out.println("Box - an other Box constructor");
        this.width=anOtherBox.width;
        this.height=anOtherBox.height;
        this.depth=anOtherBox.depth;
    }

    public Box(double width, double height, double depth){
        System.out.println("Box - full constructor");
        this.width=width;
        this.height=height;
        this.depth=depth;
    }



    public Box(double len){
        System.out.println("Box - cube constructor");
        this.width=len;
        this.height=len;
        this.depth=len;
    }

    */

    public Box(double a){
        System.out.println("Box - default constructor");
        this.width=0;
        this.height=0;
        this.depth=0;
    }

    public int volume(){
        System.out.println("Box - volume");
        return (int)(this.width*this.height*this.depth);
    }

    public static final void doSmth(){
        System.out.println("Box - doSmth");
    }

    public void doSmth(String formalParameter){
        System.out.println("Box - doSmth - formalParameter:"+formalParameter);
    }


}
