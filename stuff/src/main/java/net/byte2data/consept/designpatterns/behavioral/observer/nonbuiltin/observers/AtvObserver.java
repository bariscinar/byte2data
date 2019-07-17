package net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers;

import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.publishers.Publisher;
import net.byte2data.consept.designpatterns.behavioral.observer.data.intf.IWeather;

public class AtvObserver implements Observer {

    public AtvObserver(Publisher publisher){
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
        System.out.println("ATV update - 1 - FeelingTemp:" + iWeather.getFeelingTemp().getValue());
    }

    @Override
    public void update(double... a) {
        System.out.println("ATV update - 2");
    }

}
