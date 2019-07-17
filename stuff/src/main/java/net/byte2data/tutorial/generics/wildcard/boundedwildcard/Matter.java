package net.byte2data.tutorial.generics.wildcard.boundedwildcard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Matter {

    //These two methods below should have same signature.
    // Right.
    // But what about the next bundle below these samples?
    /*
    static <T> void printList(List<T> integerList){
        for(T item :  integerList){
            System.out.println("Integer item:" + item);
        }
    }
    */
    static <T extends Number> void printList(List<T> numberList){
        for(T item :  numberList){
            System.out.println("Number item:" + item);
        }
    }

    //These two methods below seem have same signature but not! How come?
    static <T> void printAnyArray(T[] anyArray){
        for(T element : anyArray){
            System.out.println("element1:"+element);
        }
    }
    //Sample for bounded type parameters is below
    static <T extends Integer> void printAnyArray(T[] anyArray){
        for(T element : anyArray){
            System.out.println("element2:"+element);
        }
    }

    //Wildcards are designed to support flexible subtyping.
    private static void printExtendNumberList(List<? extends Number> numberList){
        /*
        You should be able to figure out why the code below is disallowed.
        The type of the second parameter to numberList.addToTail() is ? extends Number--
        an unknown subtype of Number.
        Since we don't know what type it is, we don't know if it is a supertype of Integer;
        it might or might not be such a supertype, so it isn't safe to pass a Integer there.
        */
        //numberList.addToTail(0,new Integer(11));
        for(Number item :  numberList){
            System.out.println("Number item:" + item);
        }
    }

    private static<E> void printAnyList(List<E> anyList){
        anyList.add(0,anyList.get(0));
        for(E item :  anyList){
            System.out.println("E item:" + item);
        }
    }

    private static <E> void printAnyCollection(Collection<E> anyCollection){
        for(E item :  anyCollection){
            System.out.println("E item:" + item);
        }
    }

    static <X extends Comparable<X>> X whatIsMax(X... items){
        int itemCount = items.length;
        X max = items[0];
        if(itemCount>1){
            for(int index=0; index<items.length-1; index++){
                System.out.println("max:"+max);
                System.out.println("item:"+items[index+1]);

                if (max.compareTo(items[index+1])<1){
                   max=items[index+1];
                }
                System.out.println("new max:"+max);
                System.out.println("---");
            }
        }
        return max;
    }

    public static void main(String... args){
        List<String> stringList = new ArrayList<>();
        List<Number> numberList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();

        Number number1 = 1.1D;
        Number number2 = 2.2F;
        Number number3 = 3L;
        Number number4 = 4;

        Integer integer1 = 1;
        Integer integer2 = 2;
        int integer3 = 3;

        String string1 = null;
        String string2 = "bir";
        String string3 = "iki";

        numberList.add(integer1);
        numberList.add(integer2);
        numberList.add(integer3);
        numberList.add(number1);
        numberList.add(number2);
        numberList.add(number3);
        numberList.add(number4);

        integerList.add(integer1);
        integerList.add(integer2);
        integerList.add(integer3);

        stringList.add(string1);
        stringList.add(string2);
        stringList.add(string3);

        printList(numberList);
        printList(integerList);

        printExtendNumberList(numberList);
        printExtendNumberList(integerList);

        printAnyList(numberList);
        printAnyList(integerList);
        printAnyList(stringList);

        printAnyCollection(numberList);
        printAnyCollection(integerList);
        printAnyCollection(stringList);

        System.out.println(whatIsMax(1,5,32,45,0,9,2,9));
    }
}
