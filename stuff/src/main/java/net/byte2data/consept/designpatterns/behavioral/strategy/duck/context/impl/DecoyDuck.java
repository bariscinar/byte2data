package net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoFly;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoQuack;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IQuackBehavior;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.Duck;

public class DecoyDuck extends Duck{


    public DecoyDuck(IFlyBehavior flyBehavior, IQuackBehavior quackBehavior) {
        super(flyBehavior, quackBehavior);
    }

    @Override
    public void display() {
        System.out.println("DecoyDuck");
    }



}
