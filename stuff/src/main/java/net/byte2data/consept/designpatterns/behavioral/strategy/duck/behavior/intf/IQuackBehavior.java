package net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf;

public interface IQuackBehavior {
    public default void quack(){
        System.out.println("DEFAULT QUACK");
    }
}
