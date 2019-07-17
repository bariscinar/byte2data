package net.byte2data.consept.designpatterns.behavioral.observer.builtin.observers;

import net.byte2data.consept.designpatterns.behavioral.observer.data.intf.IWeather;
import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers.Observer;
import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.publishers.Publisher;

public class KanalDObserver implements Observer {

    public KanalDObserver(Publisher publisher){
        publisher.register(this);
    }

    public void registerToPublisher(Publisher publisher){
        publisher.register(this);
    }

    public void unregisterFromPulisher(Publisher publisher){
        publisher.unregister(this);
    }

    @Override
    public void update(IWeather iWeather) {
        System.out.println("KanalD update - 1 - Humidity:" + iWeather.getHumidity().getValue());
    }

    @Override
    public void update(double... a) {
        System.out.println("KanalD update - 2");
    }

}
