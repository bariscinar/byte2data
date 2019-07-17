package net.byte2data.tutorial.collections;

/**
 * Created by barisci on x1/29/16.
 */
public class Box implements Comparable<Box>{

    private String name;
    private double mass, volume;

    public Box() {
    }

    private Box(String name) {
        this.name = name;
    }

    public Box(String name, double mass, double volume) {
        this.name = name;
        this.mass = mass;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Box{" + "name:'" + name + ",mass:" + mass + ",volume:" + volume + '}';
    }

    @Override
    public int compareTo(Box otherBox) {
        return Double.compare(this.getMass(),otherBox.getMass());
    }

    @Override
    public boolean equals(Object obj) {
        if ((null != obj) && ((obj instanceof Box) && (this.volume*this.mass==(((Box) obj).volume * ((Box) obj).mass))))
            return true;
        return false;
    }
}
