package net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.publishers;

import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers.Observer;

public abstract interface Publisher {
    public abstract void register(Observer observer);
    void unregister(Observer observer);
    abstract void notifyObservers();
}
