package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IPunchBehavior;

public class Slap implements IPunchBehavior{

    @Override
    public void punch() {
        System.out.println("SLAP PUNCHING!!");
    }
}
