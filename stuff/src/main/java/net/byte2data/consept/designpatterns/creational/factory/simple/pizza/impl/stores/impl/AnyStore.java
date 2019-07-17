package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories.APizzaFactory;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores.AStore;

public class AnyStore extends AStore {

    public AnyStore(APizzaFactory pizzaFactory) {
        super(pizzaFactory);
        this.aPizzaFactory=pizzaFactory;
    }

}
