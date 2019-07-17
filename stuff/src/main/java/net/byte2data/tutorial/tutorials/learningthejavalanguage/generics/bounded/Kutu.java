package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.bounded;

/**
 * Created by barisci on 26.09.2017.
 */
public class Kutu extends Paket {

    private Standart standart;

    public enum Standart{
        TRAIN, PLAIN, SHIP, ROAD
    }
    public Kutu(double hight, double length, double witdh, String name,Standart standart) {
        super(hight, length, witdh, name);
        this.standart=standart;
    }

    public Standart getStandart() {
        return standart;
    }

    public void setStandart(Standart standart) {
        this.standart = standart;
    }


}
