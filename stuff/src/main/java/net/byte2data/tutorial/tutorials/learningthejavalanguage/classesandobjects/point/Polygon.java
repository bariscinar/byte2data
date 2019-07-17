package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.point;

/**
 * Created by barisci on 06.09.2017.
 */
public class Polygon {

    public Polygon(Point... points){
        int cornerCount = points.length;
        System.out.println("corner count: " + cornerCount);
        System.out.println("point instance count: " + Point.getInstanceCount());
    }

    public Polygon(Point point, double delta){
        point.setX(point.getX()+delta);
        point.setY(point.getY()+delta);
        point = new Point(0,0,"zero");
        point = null;
        //System.out.println("point instance count: " + Point.getInstanceCount());
    }

    /*
    already defined
    public Polygon(point[] points){
        int cornerCount = points.length;
        System.out.println("corner count:" + cornerCount);
    }
    */

}
