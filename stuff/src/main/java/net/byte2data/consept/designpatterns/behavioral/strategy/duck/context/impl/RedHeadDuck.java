package net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IQuackBehavior;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.Duck;

public class RedHeadDuck extends Duck{

    public RedHeadDuck(IFlyBehavior flyBehavior, IQuackBehavior quackBehavior){
        super(flyBehavior,quackBehavior);
    }

    @Override
    public void display() {
        System.out.println("RedHeadDuck");
    }
}
