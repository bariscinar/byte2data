package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.point;

/**
 * Created by barisci on 06.09.2017.
 */
public class Point {
    private double x;
    private double y;
    private String name;

    /*
    public static varType myvar = initializeClassVariable();
    private static varType initializeClassVariable(){
    }

    private varType2 myVar2 = initializeInstanceVariable();
    protected final varType2 initializeInstanceVariable() {
        // initialization code goes here
    }

    */
    private static int instanceCount;
    private int instanceId;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getInstanceCount() {
        return instanceCount;
    }

    private static void setInstanceCount(int instanceCount) {
        Point.instanceCount = instanceCount;
    }

    public int getInstanceId() {
        return instanceId;
    }

    private void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }

    public Point(double x, double y, String name) {

        {
            this.x = x;
            this.y = y;
            this.name = name;
            instanceId= ++instanceCount;
        }
    }
}
