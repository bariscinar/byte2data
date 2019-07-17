package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.test;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.components.APizza;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories.impl.ChicagoPizzaFactory;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.factories.impl.NYPizzaFactory;
import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.stores.impl.AnyStore;

public class TestPizza {

    public static void main(String... args){
        String store="ny";
        String condiment = "cheese";

        APizza aPizza = null;

        switch (store){
            case "chicago":
                aPizza = new AnyStore(new ChicagoPizzaFactory()).createPizza(condiment);
                break;
            case "ny":
                aPizza = new AnyStore(new NYPizzaFactory()).createPizza(condiment);
                break;
            default:
                break;
        }


        showDetails(aPizza);


    }

    private static void showDetails(APizza aPizza){
        System.out.println("type:" + aPizza.getType());
        System.out.println("price:" + aPizza.getPrice());
        System.out.print("delivery:"); aPizza.applyDeliver();
    }

}
