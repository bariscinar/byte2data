package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.IDeliver;

public abstract class APizza {

    protected double price;
    protected String type;

    protected IDeliver deliver;


    public APizza(IDeliver deliver){
        this.deliver=deliver;
    }

    public String getType() {
        return this.type;
    }

    public abstract double getPrice();

    public void setDeliver(IDeliver deliver) {
        this.deliver = deliver;
    }

    public void applyDeliver(){
        this.deliver.deliver();
    }
}
