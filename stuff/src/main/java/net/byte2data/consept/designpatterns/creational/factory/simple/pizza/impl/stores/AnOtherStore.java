package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;

public abstract class AnOtherStore {

    public APizza orderPizza(String ingredient){
        return createPizza(ingredient);
    }

    public abstract APizza createPizza(String ingredient);

}
