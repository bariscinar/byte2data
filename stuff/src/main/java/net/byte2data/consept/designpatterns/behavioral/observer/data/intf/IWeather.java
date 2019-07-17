package net.byte2data.consept.designpatterns.behavioral.observer.data.intf;

import net.byte2data.consept.designpatterns.behavioral.observer.data.FeelingTemp;
import net.byte2data.consept.designpatterns.behavioral.observer.data.Humidity;
import net.byte2data.consept.designpatterns.behavioral.observer.data.Pressure;
import net.byte2data.consept.designpatterns.behavioral.observer.data.Temperature;

public interface IWeather {

    public default Pressure getPressure(){
        return new Pressure();
    }
    public abstract Humidity getHumidity();

    Temperature getTemperature();

    default FeelingTemp getFeelingTemp(){
        return new FeelingTemp();
    }

}
