package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.stuff;

/**
 * Created by barisci on 14.08.2017.
 */
public class Bicycle implements IntBicycle {

    private Bicycle bicycle;

    private int cadence=0;
    private int speed=0;
    private int gear=0;

    public void speedUp(int inc){
        speed = speed+inc;
    }

    public void applyBreak(int dec){
        speed = speed-dec;
    }

    public void setGear(int gear){
        this.gear = gear;
    }

    public void setCadence(int cadence){
        this.cadence = cadence;
    }

    /*
    public Bicycle(){
        super();
        bicycle = new Bicycle();
    }
    */

    /*
    public Bicycle(int cadence, int speed, int gear){
        this.cadence=cadence;
        this.speed=speed;
        this.gear=gear;
        bicycle = new Bicycle(cadence,speed,gear);
    }
    */

    public Bicycle getABic(){
        return bicycle;
    }


}
