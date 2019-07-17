package net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.concrete;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.Duck;

public class RubberDuck extends Duck {

    @Override
    public void display() {
        System.out.println("RubberDuck - Display");
    }
}
