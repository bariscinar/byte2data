package net.byte2data.consept.designpatterns.behavioral.observer.data;

import net.byte2data.consept.designpatterns.behavioral.observer.data.intf.IWeather;

import java.util.Random;

public class GetDataFromStore {
    private IWeather weather;

    public IWeather fetch(){
        Data data = new Data();
        data.getHumidity();
        data.getPressure();
        data.getTemperature();
        data.getFeelingTemp();
        return data;
    }

    private class Data implements IWeather{

        private Temperature temperature;
        private Pressure pressure;
        private Humidity humidity;
        private FeelingTemp feelingTemp;

        Data(){
            temperature=new Temperature();
            pressure=new Pressure();
            humidity=new Humidity();
            feelingTemp=new FeelingTemp();
        }

        @Override
        public Pressure getPressure() {
            this.pressure.setValue(new Random().nextInt(10));
            return this.pressure;
        }

        @Override
        public Humidity getHumidity() {
            this.humidity.setValue(new Random().nextInt(10));
            return humidity;
        }

        @Override
        public Temperature getTemperature() {
            this.temperature.setValue(new Random().nextInt(10));
            return temperature;
        }

        @Override
        public FeelingTemp getFeelingTemp() {
            this.feelingTemp.setValue(new Random().nextInt(1000));
            return feelingTemp;
        }
    }

}
