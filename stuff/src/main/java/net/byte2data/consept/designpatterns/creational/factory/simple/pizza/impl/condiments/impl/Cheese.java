package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.condiments.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.condiments.ADecoratorPizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.IDeliver;

public class Cheese extends ADecoratorPizza {

    private APizza pizza;
    public Cheese(APizza pizza, String name, double cost, IDeliver deliver) {
        super(pizza, name, cost, deliver);
        this.pizza=pizza;
    }
}
