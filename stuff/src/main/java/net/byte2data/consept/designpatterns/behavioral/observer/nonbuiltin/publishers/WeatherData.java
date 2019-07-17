package net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.publishers;

import net.byte2data.consept.designpatterns.behavioral.observer.data.GetDataFromStore;
import net.byte2data.consept.designpatterns.behavioral.observer.data.intf.IWeather;
import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers.Observer;

import java.util.*;

public class WeatherData implements Publisher {

    private IWeather iWeather;

    private Map<Observer, IWeather> observerList;

    public WeatherData(){
        observerList = new HashMap<>();

    }

    public void fetchData(){
        GetDataFromStore getData = new GetDataFromStore();
        this.iWeather = getData.fetch();
        notifyObservers();
    }

    @Override
    public void register(Observer observer) {
        observerList.putIfAbsent(observer, null);
    }

    //Overloading
    public void register(Observer observer, IWeather iWeather) {
        observerList.putIfAbsent(observer, iWeather);
    }

    @Override
    public void unregister(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if(observerList.size()>0){
            for (Observer singleObserver : observerList.keySet()){
                if(null==observerList.get(singleObserver)){
                    singleObserver.update(this.iWeather);
                }else{
                    singleObserver.update(this.iWeather);
                }

            }
        }
    }
}
