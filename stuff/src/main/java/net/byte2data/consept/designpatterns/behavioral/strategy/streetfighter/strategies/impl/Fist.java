package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IPunchBehavior;

public class Fist implements IPunchBehavior{

    @Override
    public void punch() {
        System.out.println("FIST PUNCHING!!");
    }
}
