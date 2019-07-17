package net.byte2data.tutorial.generics.alltogether;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Generics allow you to abstract over TYPES.

- Provides Stronger type checks at compile time.
- Elimination of casts.
- Enabling programmers to implement generic algorithms???
By using generics,
programmers can implement generic algorithms
that work on collections of different types,
can be customized, and are type safe and easier to read.
Bounded type parameters are key to
the implementation of generic algorithms.

GENERICS JARGON
Formal Value Parameters-Variables in the Method Definition - (String strParam)
Formal Type Parameter-Variables in the Generic Method Definition - <E> (List<E> typeParam, E type)
Actual Value Argument in the Method Call - (new String(""));
Actual Type Argument in the Generic Method Call - (new ArrayList<Integer>, new Integer(0))

Actual Type Argument is also called Parameterized Type -> ArrayList<Integer>
*/

public class Matter<T> {

    private T tTypeInstanceVariable;
    private T instanceMethod(T param){
        return param;
    }

    /*
    A generic type declaration is compiled once and for all,
    and turned into a single class file,
    just like an ordinary class or interface declaration.
    There aren't multiple copies of the code--not in source,
    not in binary, not on disk and not in memory.
    If you are a C++ programmer,
    you'll understand that this is very different than a C++ template.

    All instances of a generic class have the same run-time class,
    regardless of their actual type parameters.

    That is why it is illegal to refer to the type parameters of a type declaration,
    in a static method or initializer,
    or in the declaration or initializer of a static variable.
    */
    /*
    private static T tTypeClassVariable;
    private static T classMethod(T param){
        return param;
    }
    */

    private static <E> E otherClassMethod(E param){
        return param;
    }

    public static void main(String... args){

        /*
        Generics are checked at compile-time for type-correctness.
        The generic type information is then removed in a process called TYPE ERASURE.
        For example, List<Integer> will be converted to the non-generic type List,
        which ordinarily contains arbitrary objects.
        The compile-time check guarantees that the resulting code is type-correct.

        Because of type erasure, type parameters cannot be determined at run-time.
        For example, when an ArrayList is examined at runtime,
        there is no general way to determine whether, before type erasure,
        it was an ArrayList<Integer> or an ArrayList<Float>.
        */

        List<String> stringList = new ArrayList<String>();
        List<Integer> integerList = new ArrayList<Integer>();

        System.out.println("Class of stringList -> " + stringList.getClass());
        System.out.println("Class of integerList -> " + integerList.getClass());

        String[] stringArray = new String[1];
        Integer[] integerArray  = new Integer[1];
        System.out.println("Class of stringArray -> " + stringArray.getClass());
        System.out.println("Class of integerArray -> " + integerArray.getClass());

        /*
        SUBTYPING
        */
        Object obj  = 1;
        String string = "bir";
        obj = string;

        /*
        In object-oriented terminology, this is called an "is a" relationship.
        Since an String is a kind of Object, the assignment is allowed.
        */
        Object[] objects = {1,new Object(),"",null};
        String[] strings = {"bir","iki","üç","dört"};
        objects = strings;
        System.out.println("fourth element of string array: " + strings[3]);
        System.out.println("fourth element of object array: " + objects[3]);
        //objects[3] = 99;
        //ArrayStoreException
        /*
        The code indeed compiles, but the error will be raised
        at runtime as an ArrayStoreException.
        Because of this behavior of arrays, during a store operation,
        the Java runtime needs to check that the types are compatible.
        The check, obviously,
        also adds a performance penalty that you should be aware of.
        */

        List<Object> listObject = new ArrayList<>();
        List<String> listString = new ArrayList<>();
        //listObject = listString;
        /*
        Accessing listString, a list of String, through the alias listObject,
        we can insert arbitrary objects into it.
        As a result listString does not hold just Strings anymore,
        and when we try and get something out of it, we get a rude surprise.

        In general, if Foo is a subtype (subclass or subinterface) of Bar,
        and G is some generic type declaration,
        it is not the case that G<Foo> is a subtype of G<Bar>.
        This is probably the hardest thing you need to learn about generics,
        because it goes against our deeply held intuitions.
        We should not assume that collections don't change.
        Our instinct may lead us to think of these things as immutable.
        */
        /*
        Once more, generics are safer to use and "correct" this type safety weakness of Java arrays.
        In the case you're now wondering why the subtyping relation for arrays is COVARIANT;

        *???* If it was INVARIANT as shown in GENERICS,
        there would be no way of passing a reference to an array of objects of an unknown type
        (without copying every time to an Object[]) to a method such as:
        void sort(Object[] o);
        */

        /*
        So what is the supertype of all kinds of collections?
        It's written Collection<?> (pronounced "collection of unknown"),
        that is, a collection whose element type matches anything.
        It's called a wildcard type for obvious reasons.
        */

        List<? extends Number> numberList2 = new ArrayList<>();
        List<Integer> integerList2 = new ArrayList<>();
        numberList2 = integerList2;
        integerList2.add(99);
        System.out.println("first element of number array: " +numberList2.get(0));
        //numberList2.addToTail(100);
        /*
        No way!!
        It comes out that, indeed,
        you can't put anything into a structure whose type uses the ? extends wildcard.
        The reason is pretty simple, if we think about it:
        the ? extends T wildcard tells the compiler that we're dealing with a subtype of the type T,
        but we cannot know which one.
        Since there's no way to tell, and we need to guarantee type safety,
        you won't be allowed to put anything inside such a structure.
        */
        /*
        On the other hand, since we know that whichever type it might be,
        it will be a subtype of T,
        we can get data out of the structure with the guarantee that it will be a T instance:
        */
        /*
        You should be able to figure out why the code below is disallowed.
        The type of the second parameter to integerList2.addToTail() is ? extends Number--
        an unknown subtype of Number.
        Since we don't know what type it is, we don't know if it is a supertype of Integer;
        it might or might not be such a supertype, so it isn't safe to pass a Integer there.
        */

        List<? super Integer> numberList3 = new ArrayList<>();
        List<Integer> integerList3 = new ArrayList<>();
        numberList3 = integerList3;
        integerList3.add(99);
        numberList3.add(100);
        //int result3 = numberList3.get(0);
        int result3 = integerList3.get(0);
        System.out.println("first element of number array: " +integerList3.get(0));

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
   UPPER BOUND
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
    LOWER BOUND
    ? super reintroduces CONTRAVARIANT subtyping for generics type
    */
    private static void printLowerGenericTypeObjects(Collection<? super Integer> collection){
        Number num = 12;
        Integer inte = 12;
        collection.add(inte);
        for(Object obj : collection){
            System.out.println("object class:"+obj.getClass());
            System.out.println("object:"+obj);
        }
    }

    //*???* These two methods below seem have same signature but not! How come?
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

}
