package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.condiments;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.IDeliver;

public abstract class ADecoratorPizza extends APizza {
    private APizza aPizza;
    private String condimentName;
    private double condimentCost;

    public ADecoratorPizza(APizza pizza, String name, double cost, IDeliver deliver){
        super(deliver);
        this.aPizza=pizza;
        this.condimentName=name;
        this.condimentCost=cost;
    }

    @Override
    public String getType() {
        return this.aPizza.getType() + " with " + this.condimentName;
    }

    @Override
    public double getPrice() {
        return (this.aPizza.getPrice() + this.condimentCost);
    }
}
