package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies;

public interface IKickBehaviour {
    public default void kick(){
        System.out.println("DEFAULT KICK!");
    }
}
