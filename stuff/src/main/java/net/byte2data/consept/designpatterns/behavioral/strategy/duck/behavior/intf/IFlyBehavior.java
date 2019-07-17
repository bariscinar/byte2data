package net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf;

public interface IFlyBehavior {
    public default void fly(){
        System.out.println("DEFAULT FLY");
    }

}
