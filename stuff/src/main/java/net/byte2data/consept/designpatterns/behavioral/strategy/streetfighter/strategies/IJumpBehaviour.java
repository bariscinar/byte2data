package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies;

public abstract interface IJumpBehaviour{
    default void jump() {
        System.out.println("DEFAULT JUMP!");
    }
}
