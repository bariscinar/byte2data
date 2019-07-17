package net.byte2data.tutorial.generics.genericmethods;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Matter {

    private static class Food{

    }
    private static class Vegetable extends Food{

    }
    private static class Fruit extends Food{

    }
    private static class Tomato extends Vegetable{

    }
    private static class Cucumber extends Vegetable{

    }
    private static  class Apple extends Fruit{

    }
    private static class Orange extends Fruit{

    }
    private static class Badem extends Cucumber{

    }
    private static  class GreenApple extends Apple{

    }

    private static class Box<T> {
        List<T> tList;
        Box(){
            tList = new ArrayList<>();
        }
        T getItem(int index) {
            return tList.get(index);
        }
        void pushItem(T item) {
            tList.add(item);
        }
        void listItems(){
            int index=0;
            for(T element : tList){
                System.out.println(index++ + ". item: " + element);
            }
        }
    }
    private static class BigBox{
        private static List<Box<?>> anyBoxes = new ArrayList<>();
        static void addBox(Box<?> aBox){
            anyBoxes.add(aBox);
        }
        static void listBoxContents(){
            for(Box<?> anyBox : anyBoxes){
                anyBox.listItems();
            }
        }
    }

    /*
    The method below is a sample of generic algorithm!
    */
    static <T extends Comparable<T>> int getCount(List<T> tList, T element){
        int count = 0;
        for(T item : tList){
            if(element.compareTo(item)<0)
                count++;
        }
        return count;
    }

    public static void main(String... args){
        List<String> strA = new ArrayList<String>(){{add("bir");add("iki");add("üç");add("dört");add("beş");}};
        List<String> strB = new ArrayList<String>(Arrays.asList("bir1","iki2","üç3","dört4","beş5"));
        List<Integer> intA = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        System.out.println("generic algorithm - count of items bigger than \"dört\"-> "+getCount(strA,"dört"));
        System.out.println("generic algorithm - count of items bigger than \"iki\"-> "+getCount(strB,"dört"));
        System.out.println("generic algorithm - count of items bigger than \"4\"-> "+getCount(intA,4));

        Box<? super Apple> appleBox = new Box<>();

        appleBox.pushItem(new Apple());
        appleBox.pushItem(new GreenApple());

        Box<? super Fruit> fruitBox = new Box<>();
        fruitBox.pushItem(new Apple());
        fruitBox.pushItem(new Orange());
        fruitBox.pushItem(new GreenApple());

        List<Box<?>> foodBox = new ArrayList<>();
        foodBox.add(appleBox);
        foodBox.add(fruitBox);

        BigBox.addBox(appleBox);
        BigBox.addBox(fruitBox);
        BigBox.listBoxContents();

        Box<Double> doubleBox = new Box<>();
        Box<Integer> integerBox = new Box<>();
        Box<Long> longBox = new Box<>();
        Box<Float> floatBox = new Box<>();
        Box<String> stringBox = new Box<>();

        doubleBox.pushItem(1.1);
        doubleBox.pushItem(1.2);
        integerBox.pushItem(2);
        integerBox.pushItem(3);
        integerBox.pushItem(4);
        longBox.pushItem(5L);
        longBox.pushItem(6L);
        floatBox.pushItem(7.1F);
        floatBox.pushItem(7.2F);
        stringBox.pushItem("sekiz");
        stringBox.pushItem("dokuz");

        //Type inference
        BigBox.<Double>addBox(doubleBox);
        BigBox.addBox(integerBox);
        BigBox.addBox(longBox);
        BigBox.addBox(floatBox);
        BigBox.addBox(stringBox);

        BigBox.listBoxContents();

        //printAnyArray(strA);
        //printAnyArray(intA);

    }

    /*
    Type inference is a Java compiler's ability to look at
    each method invocation and corresponding declaration
    to determine the type argument (or arguments) that make the invocation applicable.
    The inference algorithm determines the types of the arguments and,
    if available, the type that the result is being assigned, or returned.
    Finally, the inference algorithm tries to find,
    the most specific type that works with all of the arguments.

    You can replace the type arguments required to invoke
    the constructor of a generic class with an empty set of type parameters (<>)
    as long as the compiler can infer the type arguments from the context.
    This pair of angle brackets is informally called the diamond.
    */
    static <T> T pick(T a1, T a2) {
        return a2;
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



    static void fromArrayToCollection(Object[] a, Collection<?> c) {
        for (Object o : a) {
            /*
            By now, you will have learned to avoid the beginner's mistake of,
            trying to use Collection<Object> as the type of the collection parameter.
            You may or may not have recognized that using Collection<?> isn't going to work either.
            Recall that you cannot just shove objects into a collection of unknown type.
            The way to do deal with these problems is to use generic methods.
            Just like type declarations, method declarations can be generic —
            that is, parameterized by one or more type parameters.
            */
            //Why not??
            //c.addToTail(o);
        }
    }
    static void copyFromObjectArrayTocollection(Object[] anyArray, Collection<? super Object> anyCollection){
        for(Object element : anyArray){
            anyCollection.add(element);
        }
    }

    static <T> void copyFromAnyArrayTocollection(T[] anyArray, Collection<T> anyCollection){
        for(T element : anyArray){
            anyCollection.add(element);
        }
    }


    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
        return Arrays.stream(a).map(mapperFunction).collect(Collectors.toList());
    }



    public void processStringArrayToCollection(String[] stringArray, Collection<String> stringCollection){
        for(String item : stringArray){
            stringCollection.add(item);
        }
    }

    /*
    Notice that we don't have to pass an actual type argument to a generic method.
    The compiler infers the type argument for us, based on the types of the actual arguments.
    It will generally infer the most specific type argument that will make the call type-correct.
    One question that arises is: when should I use generic methods, and when should I use wildcard types?
    Wildcards are designed to support flexible subtyping.

    Generic methods allow type parameters to be used to express dependencies,
    among the types of one or more arguments to a method and/or its return type.
    If there isn't such a dependency, a generic method should not be used.
    */

    public <E> void processAnyArrayToCollection(E[] anyArray, Collection<E> anyCollection){
        for(E item : anyArray){
            anyCollection.add(item);
        }
    }

    public <E> void processAnyArrayToCollection2(List<? extends E> anyArray, Collection<E> anyCollection){
        for(E item : anyArray){
            anyCollection.add(item);
        }
    }

    public <E, T extends E> void processAnyArrayToCollection3(List<T> anyArray, Collection<E> anyCollection){
        for(E item : anyArray){
            anyCollection.add(item);
        }
    }

}
