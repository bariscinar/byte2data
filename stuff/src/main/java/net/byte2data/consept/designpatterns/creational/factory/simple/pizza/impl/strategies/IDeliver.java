package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies;

public interface IDeliver {
    public default void deliver(){
        System.out.println("DEFAULT deliver");
    }
}
