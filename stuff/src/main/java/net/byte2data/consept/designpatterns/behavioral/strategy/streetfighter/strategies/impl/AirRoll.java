package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IRollInterface;

public class AirRoll implements IRollInterface {

    @Override
    public void roll() {
        System.out.println("AIR ROLLING!");
    }
}
