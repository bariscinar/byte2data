package net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin;

import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers.Observer;
import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers.AtvObserver;
import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.observers.KanalDObserver;
import net.byte2data.consept.designpatterns.behavioral.observer.nonbuiltin.publishers.WeatherData;

public class TestWeather {

    public static void main(String... args){
        WeatherData weatherData = new WeatherData();
        Observer kanalD = new KanalDObserver(weatherData);
        Observer kanalA = new AtvObserver(weatherData);

        weatherData.fetchData();
    }

}
