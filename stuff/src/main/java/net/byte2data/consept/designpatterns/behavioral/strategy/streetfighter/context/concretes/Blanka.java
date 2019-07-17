package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context.concretes;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context.AbstractCharacter;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IJumpBehaviour;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IKickBehaviour;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IPunchBehavior;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.IRollInterface;

public class Blanka extends AbstractCharacter {

    public Blanka(IPunchBehavior punch,
                  IJumpBehaviour jump,
                  IRollInterface roll,
                  IKickBehaviour kick){
        super(punch,jump,roll,kick);

    }

    @Override
    public void display() {
        System.out.println("FUCK YOU!!");
    }
}
