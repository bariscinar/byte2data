package net.byte2data.consept.designpatterns.behavioral.observer.builtin.observers;

import net.byte2data.consept.designpatterns.behavioral.observer.builtin.observers.intf.Fetcher;
import net.byte2data.consept.designpatterns.behavioral.observer.builtin.publishers.intf.Publisher;

import java.util.Observable;

public class AtvObserver implements Fetcher {

    Publisher publisher;

    public AtvObserver(Publisher publisher){
        this.publisher=publisher;
        publisher.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {

    }




}
