package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.point;

/**
 * Created by barisci on 06.09.2017.
 */
public class Executor {

    public static void main(String... args){
        Point point1 = new Point(1,1,"point1");
        System.out.println(point1.getName() + " - instanceid:" + point1.getInstanceId());
        Point point2 = new Point(2,2,"point2");
        System.out.println(point2.getName() + " - instanceid:" + point2.getInstanceId());
        Point point3 = new Point(3,3,"point3");
        System.out.println(point3.getName() + " - instanceid:" + point3.getInstanceId());

        Polygon myPolygon = new Polygon(point1,point2,point3);

        Polygon otherPolygon = new Polygon(point3,10);

        Rectangle rectangle1 = new Rectangle();
        Rectangle rectangle2 = new Rectangle(10,10);
        Rectangle rectangle3 = new Rectangle(new Point(0,0,"r1"));
        Rectangle rectangle4 = new Rectangle(new Point(0,0,"r2"),0,0);

        Point myPoint = new Point(1.1,2.2,"myPoint");
        Rectangle myRectangle = new Rectangle(new Point(3.3,4.4,"rectanglePoint"),100,100);
        myRectangle.setOrigin(myPoint);
        System.out.println(myRectangle.getOrigin().getName());
        myRectangle.moveThisItem(10.0,10.0);
        System.out.println(myPoint.getX());
        myPoint = myRectangle.replaceOrigin(myPoint);
        System.out.println(myPoint.getX());
    }
}
