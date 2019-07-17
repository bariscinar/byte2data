package net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.pizza.impl.strategies.IDeliver;

public class DeliverByWalk implements IDeliver {

    @Override
    public void deliver() {
        System.out.println("DeliverByWalk");
    }
}
