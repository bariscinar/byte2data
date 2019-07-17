package net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoQuack;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.Duck;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.FlyWithWings;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;

public class MallardDuck extends Duck {

    public MallardDuck(){
        super(new FlyWithWings(), new NoQuack());
    }

    @Override
    public void setFlyBehavior(IFlyBehavior flyBehavior) {
        super.setFlyBehavior(flyBehavior);
    }

    @Override
    public void display() {
        System.out.println("MallardDuck");
    }



}
