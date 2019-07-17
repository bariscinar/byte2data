package net.byte2data.consept.designpatterns.structural.decorator.drink.component;

public abstract class AbstractDrink {

    private String name;
    private double price;

    protected AbstractDrink(String name, double price){
        this.name=name;
        this.price=price;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

}
