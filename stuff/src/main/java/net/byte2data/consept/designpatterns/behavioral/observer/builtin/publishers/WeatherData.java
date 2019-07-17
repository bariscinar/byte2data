package net.byte2data.consept.designpatterns.behavioral.observer.builtin.publishers;


import net.byte2data.consept.designpatterns.behavioral.observer.builtin.publishers.intf.Publisher;
import net.byte2data.consept.designpatterns.behavioral.observer.data.*;
import net.byte2data.consept.designpatterns.behavioral.observer.data.intf.*;

public class WeatherData extends Publisher {

    private IWeather iWeather;

    public void fetchData(){
        GetDataFromStore getData = new GetDataFromStore();
        this.iWeather = getData.fetch();
        setChanged();
        notifyObservers();
    }

}
