package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.bounded;

/**
 * Created by barisci on 26.09.2017.
 */
public class Gonderim<T extends Paket> {

    private T item;

    public Gonderim(T t){
        this.item=t;
    }

    public void sendItem(){
        System.out.println(item.toString() + " is SENDING");
    }

    public <K extends Number> void inspectElemets(K k){
        System.out.println("Type of T: " + item.getClass());
        System.out.println("Type of K: " + k.getClass());
    }

    public static <X extends Comparable<X>> int greaterThan(X[] xArray, X xElement){
        int itemindex=0;
        for(X iks : xArray){
            System.out.println("iks: " + iks.toString());
            System.out.println("xElement: " + xElement.toString());
            if(iks.compareTo(xElement) > 0){

                return itemindex++;
            }

        }
        return -1;
    }
}
