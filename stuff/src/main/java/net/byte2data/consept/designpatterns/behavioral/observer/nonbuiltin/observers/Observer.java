package net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers;

import net.byte2data.consept.designpatterns.behavioral.observer.data.intf.IWeather;

public interface Observer {
    public default void update(IWeather iWeather){
        System.out.println("Observer default update - 1");
    }

    default void update(double... doubleValue){
        System.out.println("Observer default update - 2");
    }
}
