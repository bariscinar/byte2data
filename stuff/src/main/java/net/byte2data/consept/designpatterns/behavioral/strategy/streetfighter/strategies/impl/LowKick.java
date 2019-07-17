package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IKickBehaviour;

public class LowKick implements IKickBehaviour {

    @Override
    public void kick() {
        System.out.println("LOW KICKING!!");
    }
}
