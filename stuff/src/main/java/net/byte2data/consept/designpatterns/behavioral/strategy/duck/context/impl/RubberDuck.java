package net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoFly;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoQuack;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.Duck;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;

public class RubberDuck extends Duck {

    @Override
    public void display() {
        System.out.println("RubberDuck");
    }

    public RubberDuck(){
        super(new NoFly(), new NoQuack());

    }

    @Override
    public void setFlyBehavior(IFlyBehavior flyBehavior) {
        super.setFlyBehavior(flyBehavior);
    }
}

