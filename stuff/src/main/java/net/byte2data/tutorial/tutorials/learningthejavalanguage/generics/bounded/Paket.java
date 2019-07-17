package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.bounded;

/**
 * Created by barisci on 26.09.2017.
 */
public class Paket implements Comparable<Paket>{
    private double hight,length,witdh;
    private String name;

    public Paket(double hight, double length, double witdh, String name) {
        this.hight = hight;
        this.length = length;
        this.witdh = witdh;
        this.name = name;
    }

    public double getHight() {
        return hight;
    }

    public void setHight(double hight) {
        this.hight = hight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWitdh() {
        return witdh;
    }

    public void setWitdh(double witdh) {
        this.witdh = witdh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Paket o) {
        return ((int)(o.hight*o.length*o.witdh));
    }

    @Override
    public String toString() {
        return "Paket{" +
                "hight=" + hight +
                ", length=" + length +
                ", witdh=" + witdh +
                ", howBig=" + hight*length*witdh +
                ", name='" + name + '\'' +
                '}';
    }
}
