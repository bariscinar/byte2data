package net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.concrete;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.Duck;

public class RedheadDuck extends Duck {

    @Override
    public void display() {
        System.out.println("RedheadDuck - Display");
    }
}
