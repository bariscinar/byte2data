package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.bounded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by barisci on 26.09.2017.
 */
public class Calistir{

    public static <X extends Number&Comparable<X>> void tryIt(List<X> listX, X elementX){
        for(X iks : listX){
            System.out.println("iks: " + iks);
            if(elementX.compareTo(iks) < 0){
                System.out.println("d");
            }
        }
    }

    public static <Y extends Number&Comparable<Y>> void tryIt2(List<Y> listX, Y elementX){
        for(Y iks : listX){
            System.out.println("iks: " + iks);
            if(elementX.compareTo(iks) < 0){
                System.out.println("d");
            }
        }
    }


    public static void tryIt3(List<? extends Number> listX, Number elementX){
        for(Number iks : listX){
            System.out.println("iks: " + iks);
            //if(elementX.compareTo(iks) < 0){
            //    System.out.println("d");
            //}
        }
    }

    public static void tryIt4(List<?> listX){
        for(Object iks : listX){
            System.out.println("iks: " + iks);
            //if(elementX.compareTo(iks) < 0){
            //    System.out.println("d");
            //}
        }
    }
    public static void main(String... args){
        Koli koli = new Koli(1,2,3,"koli1", Kutu.Standart.PLAIN,100);
        Gonderim<Koli> koliGonderim = new Gonderim<>(koli);
        koliGonderim.sendItem();
        Byte byteX = 0b11;
        koliGonderim.inspectElemets(0b11);

        Paket pack1 = new Paket(2.8,3.8,6.1d,"pack1");
        Paket pack2 = new Paket(2.9d,3.9d,5.9d,"pack2");
        Paket pack3 = new Paket(3.1,4.1,5.1,"pack3");
        Paket[] packArray = new Paket[]{pack1,pack2,pack3};
        Paket pack4 = new Paket(3,4,6,"pack4");

        System.out.println(koliGonderim.<Paket>greaterThan(packArray,pack4));


        List<Double> listDouble = new ArrayList<>();
        listDouble.add(11d);
        listDouble.add(12d);
        Calistir.<Double>tryIt(listDouble, 1.2d);

        List<String> listString = new ArrayList<>();
        listString.add("as");

        List<String> stringList = new ArrayList<>(Arrays.asList("1","2"));
        List<String> stringList2 = Arrays.asList("1","2");
        List<Koli> koliList = new ArrayList<>(Arrays.asList(new Koli(1,1,1,"1", Kutu.Standart.PLAIN,1),new Koli(2,2,2,"2", Kutu.Standart.PLAIN,2)));
        Calistir.tryIt4(koliList);

    }
}
