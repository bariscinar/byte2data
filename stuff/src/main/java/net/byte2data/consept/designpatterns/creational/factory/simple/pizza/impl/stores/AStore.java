package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories.APizzaFactory;

public abstract class AStore {

    protected APizzaFactory aPizzaFactory;

    public AStore(APizzaFactory pizzaFactory){
        this.aPizzaFactory=pizzaFactory;
    }

    public APizza createPizza(String ingredient){
        return this.aPizzaFactory.createPizza(ingredient);
    }

}
