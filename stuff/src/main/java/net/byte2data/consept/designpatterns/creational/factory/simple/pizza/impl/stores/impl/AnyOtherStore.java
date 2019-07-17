package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores.AnOtherStore;

public class AnyOtherStore extends AnOtherStore {


    @Override
    public APizza createPizza(String ingredient) {
        return null;
    }
}
