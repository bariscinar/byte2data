package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies;

public abstract interface IPunchBehavior {

    public default void punch(){
        System.out.println("DEFAULT PUNCH!");
    }

}
