package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.impl.ThinPizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.condiments.impl.Cheese;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories.APizzaFactory;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.impl.DeliverByWalk;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.impl.DeliverWithBike;

public class ChicagoPizzaFactory extends APizzaFactory {

    @Override
    public APizza createPizza(String ingredient) {
        switch (ingredient){
            case "cheese":
                return new Cheese(new ThinPizza("Chicago style thin pizza", 15, new DeliverWithBike()), "hot double cheese", 20, new DeliverByWalk());
            default:
                return null;
        }

    }
}
