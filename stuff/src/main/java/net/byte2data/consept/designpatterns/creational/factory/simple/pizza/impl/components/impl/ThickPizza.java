package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.IDeliver;

public class ThickPizza extends APizza {

    public ThickPizza(String type, double cost, IDeliver deliver){
        super(deliver);
        this.type=type;
        this.price=cost;
    }

    @Override
    public double getPrice() {
        System.out.println("ThickPizza has %10 KDV");
        return this.price*1.1;
    }
}
