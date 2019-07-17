package net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;

public class FlyWithWings implements IFlyBehavior {

    @Override
    public void fly() {
        System.out.println("FLY WITH WINGS!");
    }
}
