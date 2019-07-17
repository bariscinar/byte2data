package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies;

public abstract interface IRollInterface {

    public default void roll(){
        System.out.println("DEFAULT ROLL!");
    }
}
