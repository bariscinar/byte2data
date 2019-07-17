package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.point;

/**
 * Created by barisci on 06.09.2017.
 */
public class Rectangle {

    private double length;
    private double width;
    private Point origin;

    public Rectangle(){
        this.setLength(0);
        this.setWidth(0);
        this.setOrigin(new Point(0,0,"rect1"));
    }

    public Rectangle(double width, double length){
        this.setLength(length);
        this.setWidth(width);
        this.setOrigin(new Point(0,0,"rect2"));
    }

    public Rectangle(Point p){
        this.setLength(0);
        this.setWidth(0);
        this.setOrigin(p);
    }

    public Rectangle(Point p, double width, double length){
        this.setLength(length);
        this.setWidth(width);
        this.setOrigin(p);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public void moveThisItem(double x, double y){
        this.origin.setX(origin.getX()+x);
        this.origin.setY(origin.getY()+y);
    }

    public void removeOrigin(){
        this.origin=null;
    }

    public Point replaceOrigin(Point origin){
        this.origin.setX(origin.getX());
        this.origin.setY(origin.getY());
        origin = new Point(0.0,0.0,"zero");
        return origin;
    }
}
