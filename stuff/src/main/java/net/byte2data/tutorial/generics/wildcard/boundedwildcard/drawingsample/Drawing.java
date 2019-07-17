package net.byte2data.tutorial.generics.wildcard.boundedwildcard.drawingsample;

import java.util.ArrayList;
import java.util.List;

public class Drawing {

    private class Canvas{
        public void draw(Shape shape){
            System.out.println("Canvas.draw called with Shape:"+shape);
            shape.draw(this);
        }

        public void drawAll(List<Shape> shapeList){
            System.out.println("Canvas.drawAll called with Shape:"+shapeList);
            for(Shape shape : shapeList){
                shape.draw(this);
            }
        }

        public void drawAllSuperType(List<? extends Shape> shapeList){
            System.out.println("Canvas.drawAll called with Shape:"+shapeList);
            for(Shape shape : shapeList){
                shape.draw(this);
            }
        }
    }

    private abstract class Shape{
        public abstract void draw(Canvas c);
    }

    private class Circle extends Shape{
        private double x,y, radius;
        public void draw(Canvas c){
            System.out.println("Circle.draw called with Canvas:"+c);
            //c.draw(this);
        }
    }

    private class Rectangle extends Shape{
        private double x,y,width,height;
        public void draw(Canvas c){
            System.out.println("Rectangle.draw called with Canvas:"+c);
            //c.draw(this);
        }
    }

    public static void main(String... args){
        Drawing drawing = new Drawing();
        Canvas canvas = drawing.new Canvas();

        Circle circle1 = drawing.new Circle();
        Rectangle rectangle1 = drawing.new Rectangle();
        Circle circle2 = drawing.new Circle();
        Rectangle rectangle2 = drawing.new Rectangle();
        Circle circle3 = drawing.new Circle();
        Rectangle rectangle3 = drawing.new Rectangle();

        List<Shape> shapes = new ArrayList<>();
        shapes.add(circle1);
        shapes.add(rectangle1);
        shapes.add(circle2);
        shapes.add(rectangle2);
        shapes.add(circle3);
        shapes.add(rectangle3);

        List<Circle> circles = new ArrayList<>();
        circles.add(circle1);

        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(rectangle1);

        //circle.draw(canvas);
        //rectangle.draw(canvas);

        //canvas.draw(circle);
        //canvas.draw(rectangle);

        canvas.drawAll(shapes);
        /*
        canvas.drawAll(circles);
        canvas.drawAll(rectangles);
        */

        canvas.drawAllSuperType(circles);
        canvas.drawAllSuperType(rectangles);
    }

}
