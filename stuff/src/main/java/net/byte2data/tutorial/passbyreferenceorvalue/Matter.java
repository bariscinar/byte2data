package net.byte2data.tutorial.passbyreferenceorvalue;

import java.util.concurrent.atomic.AtomicReference;

public class Matter {

    private class Point{
        private double x,y;
        Point(double xPoint, double yPoint){
            this.x = xPoint;
            this.y = yPoint;
        }
        void setX(double x){
            this.x = x;
        }
        void setY(double y){
            this.y = y;
        }
        double[] getOrigins(){
            double[] origins= {x,y};
            return origins;
            /*
            why can't the statement below permitted?
            Array initializer is not allowed here
            return {x,y};
            */
        }
    }
    private void goodSwap(Point p1, Point p2){
        p1.setX(3.11);
        p1.setY(3.22);
        Point temp = new Point(p1.x,p1.y);
        p1.setX(p2.x);
        p1.setY(p2.y);
        p2.x=temp.x;
        p2.y=temp.y;
    }

    private void badSwap(Point p1, Point p2){
        p1.setX(3.11);
        p1.setY(3.22);
        p2=p1;
    }

    private void check(int x, Integer y){
        System.out.println("x:"+x);
        System.out.println("y:"+y);
        x*=x;
        y*=y;
        System.out.println("new x:"+x);
        System.out.println("new y:"+y);
    }
    private void checkMutate(int x, AtomicReference<Integer> y){
        System.out.println("x:"+x);
        System.out.println("y:"+y);
        x*=x;
        y.set(y.get()*y.get());
        System.out.println("new x:"+x);
        System.out.println("new y:"+y);
    }

    private void swapObject(Object obj){
        System.out.println("obj:"+obj);
        obj = new String("New Object");
        System.out.println("obj:"+obj);
    }
    private void swapObjectByRef(AtomicReference<Object> obj){
        System.out.println("obj:"+obj.get());
        obj.set(new String("New Object"));
        System.out.println("obj:"+obj.get());
    }



    void prove(){
        AtomicReference<Point> pointAtomicReference1 = new AtomicReference<>();
        AtomicReference<Point> pointAtomicReference2 = new AtomicReference<>();

        Point point1  =new Point(1.11,1.22);
        Point point2 = new Point(2.11,2.22);

        System.out.println("INITIAL");
        System.out.println("point1.X:"+point1.x);
        System.out.println("point1.Y:"+point1.y);
        System.out.println("point2.X:"+point2.x);
        System.out.println("point2.Y:"+point2.y);

        goodSwap(point1,point2);
        System.out.println("AFTER GOOD SWAP");
        System.out.println("point1.X:"+point1.x);
        System.out.println("point1.Y:"+point1.y);
        System.out.println("point2.X:"+point2.x);
        System.out.println("point2.Y:"+point2.y);

        //badSwap(point1,point2);
        //System.out.println("AFTER BAD SWAP");
        //System.out.println("point1.X:"+point1.x);
        //System.out.println("point1.Y:"+point1.y);
        //System.out.println("point2.X:"+point2.x);
        //System.out.println("point2.Y:"+point2.y);



        int xx = 10;
        Integer yy = new Integer(20);
        System.out.println("initial xx:"+xx);
        System.out.println("initial yy:"+yy);
        check(xx,yy);
        System.out.println("final xx:"+xx);
        System.out.println("final yy:"+yy);

        AtomicReference<Integer> integerAtomicReference = new AtomicReference<>();
        integerAtomicReference.set(yy);
        checkMutate(xx,integerAtomicReference);
        System.out.println("mutate xx:"+xx);
        System.out.println("mutate integerAtomicReference:"+integerAtomicReference.get());

        Object localObj = new Object();
        System.out.println("1 - localObject:"+localObj);
        swapObject(localObj);
        System.out.println("2 - localObject:"+localObj);
        System.out.println("---");
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        atomicReference.set(localObj);
        System.out.println("1 - atomicReference:"+atomicReference.get());
        swapObjectByRef(atomicReference);
        System.out.println("2 - atomicReference:"+atomicReference.get());
    }

    public static void main(String... args){
        Matter matter = new Matter();
        matter.prove();
    }

}
