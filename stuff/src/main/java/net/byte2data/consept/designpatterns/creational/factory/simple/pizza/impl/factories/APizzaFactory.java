package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;

public abstract class APizzaFactory {
    public abstract APizza createPizza(String type);
}
