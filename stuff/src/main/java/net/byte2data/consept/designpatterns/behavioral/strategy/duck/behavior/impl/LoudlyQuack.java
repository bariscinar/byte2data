package net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IQuackBehavior;

public class LoudlyQuack implements IQuackBehavior {

    @Override
    public void quack() {
        System.out.println("LOUDLY QUACK");
    }
}
