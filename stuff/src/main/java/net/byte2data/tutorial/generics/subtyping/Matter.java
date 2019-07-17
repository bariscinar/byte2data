package net.byte2data.tutorial.generics.subtyping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
In more formal words, the subtyping relation between generic types is invariant.
*/

public class Matter {
    /*
    There is no differences between first and second class definitions
    */
    private class ObjectResource {
        private Collection collectionResource;
        ObjectResource(Collection collection){
            this.collectionResource=collection;
        }
        void printElements(){
            for(Object obj : collectionResource){
                System.out.println("ObjectResource element:"+obj);
            }
        }
    }
    private class ObjectTypeResource {
        private List<Object> collectionResource;
        ObjectTypeResource(List<Object> collection){
            this.collectionResource=collection;
        }
        void printElements(){
            for(Object item : collectionResource){
                System.out.println("ObjectTypeResource:"+item);
            }
        }
    }

    private class ParameterizedGenericTypeResource<E> {
        private Collection<E> collectionResource;
        ParameterizedGenericTypeResource(Collection<E> collection){
            this.collectionResource=collection;
        }
        void printElements(){
            for(E item : collectionResource){
                System.out.println("ParameterizedTypeResource element:"+item);
            }
        }
    }
    private class ParameterizedGenericUpperBoundedTypeResource<E extends Number> {
        private Collection<E> collectionResource;
        ParameterizedGenericUpperBoundedTypeResource(Collection<E> collection){
            this.collectionResource=collection;
        }
        void printElements(){
            for(E item : collectionResource){
                System.out.println("ParameterizedGenericUpperBoundedTypeResource element:"+item);
            }
        }
    }
    /*
    ????
    Why "super" cannot be used in the lower bounded class definition??
    private class ParameterizedGenericLowerBoundedTypeResource<E super Integer> {
        private Collection<E> collectionResource;
        ParameterizedGenericLowerBoundedTypeResource(Collection<E> collection){
            this.collectionResource=collection;
        }
        void printElements(){
            for(E item : collectionResource){
                System.out.println("ParameterizedGenericLowerBoundedTypeResource element:"+item);
            }
        }
    }
    */

    private static void printObjects(Collection collection){
        for(Object obj : collection){
            System.out.println("object class:"+obj.getClass());
            System.out.println("object:"+obj);
        }
    }
    private static void printTypeObjects(Collection<Object> collection){
        for(Object obj : collection){
            System.out.println("object class:"+obj.getClass());
            System.out.println("object:"+obj);
        }
    }
    /*
    What are the differences of these two methods below?
    */
    private static <E> void printGenericTypeObjects(Collection<E> collection){
        for(E obj : collection){
            System.out.println("object class:"+obj.getClass());
            System.out.println("object:"+obj);
        }
    }
    private static void printGenericSuperTypeObjects(Collection<?> collection){
        /*
        ????
        Why cant "?" used instead of "Object"?
        */
        for(Object objType : collection){
            System.out.println("type object class:"+objType.getClass());
            System.out.println("type object:"+objType);
        }
    }
    /*
    ? extends reintroduces COVARIANT subtyping for generics type
    */
    private static void printUpperGenericTypeObjects(Collection<? extends Number> collection){
        Number num = 12;
        Integer inte = 12;
        //collection.addToTail(num);
        //collection.addToTail(inte);
        for(Number obj : collection){
            System.out.println("object class:"+obj.getClass());
            System.out.println("object:"+obj);
        }
    }
    /*
    ? super reintroduces CONTRAVARIANT subtyping for generics type
    */
    private static void printLowerGenericTypeObjects(Collection<? super Integer> collection){
        Number num = 12;
        Integer inte = 12;
        //collection.addToTail(num);
        collection.add(inte);
        for(Object obj : collection){
            System.out.println("object class:"+obj.getClass());
            System.out.println("object:"+obj);
        }
    }

    public static void main(String... args){
        Object obj1  = 1;
        String string = "bir";
        obj1 = string;
        obj1 = 2;
        System.out.println("Object:"+obj1 + " - String:"+string);

        Object[] objects = {1,new Object(),"",null};
        String[] strings = {"bir","iki","üç","dört"};
        objects = strings;

        System.out.println("fourth element of string array: " + strings[3]);
        System.out.println("fourth element of object array: " + objects[3]);
        strings[3]="dörtt";
        System.out.println("fourth element of string array: " + strings[3]);
        System.out.println("fourth element of object array: " + objects[3]);

        //ArrayStoreException
        /*
        The code indeed compiles, but the error will be raised at runtime as an ArrayStoreException.
        Because of this behavior of arrays, during a store operation,
        the Java runtime needs to check that the types are compatible.
        The check, obviously,
        also adds a performance penalty that you should be aware of.
        */
        //objects[3]=4;
        System.out.println("fourth element of string array: " + strings[3]);
        System.out.println("fourth element of object array: " + objects[3]);

        /*
        Once more, generics are safer to use and "correct" this type safety weakness of Java arrays.
        In the case you're now wondering why the subtyping relation for arrays is covariant,
        I'll give you the answer that Java Generics and Collections give:
        if it was invariant,
        there would be no way of passing a reference to an array of objects of an unknown type
        (without copying every time to an Object[]) to a method such as:

        void sort(Object[] o);

        With the advent of generics,
        this characteristics of arrays is no longer necessary
        (as we'll see in the next part of this post) and should indeed by avoided.
        */

        /*
        Accessing listString, a list of String, through the alias listObject,
        we can insert arbitrary objects into it.
        As a result listString does not hold just Strings anymore,
        and when we try and get something out of it, we get a rude surprise.
        In general, if Foo is a subtype (subclass or subinterface) of Bar,
        and G is some generic type declaration, it is not the case that G<Foo> is a subtype of G<Bar>.
        This is probably the hardest thing you need to learn about generics, because it goes against our deeply held intuitions.
        We should not assume that collections don't change.
        Our instinct may lead us to think of these things as immutable.
        */
        List<String> listString = new ArrayList<>();
        List<Object> listObject = new ArrayList<>();
        //listObject = listString;

        List<? extends Number> numberList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        numberList = integerList;
        integerList.add(45);
        /*
        //numberList.addToTail(3.3);
        No way!!
        It comes out that, indeed,
        you can't put anything into a structure whose type uses the ? extends wildcard.
        The reason is pretty simple, if we think about it:
        the ? extends T wildcard tells the compiler that we're dealing with a subtype of the type T,
        but we cannot know which one.
        Since there's no way to tell, and we need to guarantee type safety,
        you won't be allowed to put anything inside such a structure.
        numberList.addToTail(88);
        */

        /*
        On the other hand, since we know that whichever type it might be,
        it will be a subtype of T,
        we can get data out of the structure with the guarantee that it will be a T instance:
         */
        Number resultNumber = numberList.get(0);
        int resultInteger = integerList.get(0);

        List<Object> objectList = new ArrayList<>();
        List<Number> otherNumberList = new ArrayList<>();
        List<? super Integer> superIntegerList = new ArrayList<>();
        superIntegerList = objectList;
        superIntegerList = otherNumberList;
        superIntegerList.add(45);
        otherNumberList.add(2.3);
        //int result = superIntegerList.get(1);
        System.out.println("RESULT ->" + superIntegerList.get(1));


        /*
        Summarizing the behavior of the;
        ? extends
        and the
        ? super
        wildcards,
        we draw the following conclusion:
        Use the "? extends" wildcard if you need to retrieve object from a data structure.
        Use the "? super" wildcard if you need to put objects in a data structure.
        If you need to do both things, don't use any wildcard.

        This is what Maurice Naftalin calls The Get and Put Principle in his
        Java Generics and Collections and what Joshua Bloch calls The PECS Rule in his Effective Java.

        Bloch's mnemonic, PECS, comes from "Producer Extends, Consumer Super"
        and is probably easier to remember and use.
        */

        List<Number> numberArrayList = new ArrayList<>();
        numberArrayList.add(0.1);
        numberArrayList.add(1);

        List<Double> doubleArrayList = new ArrayList<>();
        doubleArrayList.add(1.1);
        doubleArrayList.add(2.2);

        List<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("bir1");
        stringArrayList.add("iki2");

        printObjects(doubleArrayList);
        printObjects(stringArrayList);

        /*
        As mentioned above, printTypeObjects method only accepts "Collection<Object>" type
        */
        //printTypeObjects(doubleArrayList);
        //printTypeObjects(stringArrayList);

        printGenericTypeObjects(doubleArrayList);
        printGenericTypeObjects(stringArrayList);
        printGenericSuperTypeObjects(doubleArrayList);
        printGenericSuperTypeObjects(stringArrayList);

        printUpperGenericTypeObjects(doubleArrayList);
        //printUpperGenericTypeObjects(stringArrayList);

        printLowerGenericTypeObjects(numberArrayList);
        //printLowerGenericTypeObjects(doubleArrayList);
        //printLowerGenericTypeObjects(stringArrayList);

        Matter matter = new Matter();
        ObjectResource resource = matter.new ObjectResource(doubleArrayList);
        resource.printElements();
        ParameterizedGenericTypeResource<Double> parameterizedTypeResource = matter.new ParameterizedGenericTypeResource(doubleArrayList);
        parameterizedTypeResource.printElements();
        /*
        As mentioned above, ParameterizedObjectTypeResource constructor only accepts "Collection<Object>" type.
        And List<Double> or List<String> is not an subtype of List<Object>
        */
        //ParameterizedObjectTypeResource parameterizedObjectTypeResourceUnchecked = matter.new ParameterizedObjectTypeResource(doubleArrayList);
        //ParameterizedObjectTypeResource parameterizedObjectTypeResourceUnchecked = matter.new ParameterizedObjectTypeResource(stringArrayList);

        ObjectResource resource2 = matter.new ObjectResource(stringArrayList);
        resource2.printElements();
        ParameterizedGenericTypeResource<String> parameterizedTypeResource2 = matter.new ParameterizedGenericTypeResource(stringArrayList);
        parameterizedTypeResource2.printElements();


        Collection<Number> numberCollection = new ArrayList<>();
        numberCollection.add(new Integer(11));
        System.out.println(numberCollection.iterator().next());

        Collection<Integer> integerCollection = new ArrayList<>();
        integerCollection.add(new Integer(11));
        System.out.println(integerCollection.iterator().next());

        /*
        Since we don't know what the element type of superCollection stands for, we cannot addToTail objects to it.
        The addToTail() method takes arguments of type E, the element type of the collection.
        When the actual type parameter is ?, it stands for some unknown type.
        Any parameter we pass to addToTail would have to be a subtype of this unknown type.
        Since we don't know what type that is, we cannot pass anything in.
        The sole exception is null, which is a member of every type.

        When the actual type argument is ?, it stands for some unknown type.
        Any method argument value we pass to the addToTail() method would have to be a subtype of this unknown type.
        Since we don't know what type that is, we cannot pass anything in.
        The sole exception is null; which is a member of every type

        */
        Collection<?> superCollection = new ArrayList<>();
        //superCollection.addToTail(new Object());
        superCollection.add(null);

        //But i do not understand below!!!
        Collection<? extends Number> upperNumberCollection = new ArrayList<>();
        //upperNumberCollection.addToTail(new Integer(11));
        upperNumberCollection.add(null);
        System.out.println(numberCollection.iterator().next());
    }
}
