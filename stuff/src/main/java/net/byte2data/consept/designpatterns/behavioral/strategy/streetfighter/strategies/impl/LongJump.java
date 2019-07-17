package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IJumpBehaviour;

public class LongJump implements IJumpBehaviour {

    @Override
    public void jump() {
        System.out.println("LONG JUMPING!!");
    }
}
