package net.byte2data.tutorial.generics.wildcard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
In generic code, the question mark (?),
called the wildcard, represents an unknown type
and also called Unbounded Wildcard.
The wildcard can be used in a variety of situations:
?as the type of a parameter, ?field, ?local variable;
sometimes as a ?return type
(though it is better programming practice to be more specific).

?The wildcard is never used as a type argument
for a generic method invocation,
a generic class instance creation, or a supertype.

There are two scenarios where an unbounded wildcard is a useful approach:
- If you are writing a method that can be implemented
using functionality provided in the Object class.
- When the code is using methods in the generic class
that don't depend on the type parameter.
For example, List.size or List.clear.
**In fact, Class<?> is so often used
because most of the methods in Class<T> do not depend on T.

So what is the supertype of all kinds of collections?
It's written Collection<?> (pronounced "collection of unknown"),
that is, a collection whose element type matches anything.
It's called a wildcard type for obvious reasons.

?Wildcards are particularly useful when using
generics and can be used as a PARAMETER TYPE.

Wildcards may only be used as REFERENCE PARAMETER!
What is REFERENCE PARAMETER?
*/

/*
One of the more confusing aspects when
learning to program with generics is,
determining when to use an upper bounded wildcard
and when to use a lower bounded wildcard.
For purposes of this discussion,
it is helpful to think of variables as
providing one of two functions:

Imagine a copy method with two arguments:
copy(src, dest)
the src argument provides the data to be copied,
so it is the "in" parameter.
In the copy example,
copy(src, dest)
the dest argument accepts data,
so it is the "out" parameter.
Of course, some variables are used both for "in" and "out" purposes,
this scenario is also addressed in the guidelines.
You can use the "in" and "out" principle when deciding,
whether to use a wildcard and what type of wildcard is appropriate.
An "in" variable is defined with an
upper bounded wildcard, using the extends keyword.
An "out" variable is defined with a
lower bounded wildcard, using the super keyword.
In the case where the "in" variable can be accessed
using methods defined in the Object class, use an unbounded wildcard.
In the case where the code needs to access the variable as
both an "in" and an "out" variable, do not use a wildcard.

A list defined by List<? extends ...> can be informally thought of as read-only,
but that is not a strict guarantee



It is possible to use both generic methods and wildcards in tandem.
Here is the method Collections.copy():
class Collections {
    public static <T> void copy(List<T> dest, List<? extends T> src) {
    ...
}
Note the dependency between the types of the two parameters.
Any object copied from the source list, src,
must be assignable to the element type T of the destination list, dst.
So the element type of src can be any subtype of T—we don't care which.
The signature of copy expresses the dependency using a type parameter,
but uses a wildcard for the element type of the second parameter.

We could have written the signature for this method another way, without using wildcards at all:

class Collections {
    public static <T, S extends T> void copy(List<T> dest, List<S> src) {
    ...
}
This is fine,
but while the first type parameter is used both in the type of dst and
in the bound of the second type parameter, S,
S itself is only used once, in the type of src—nothing else depends on it.
This is a sign that we can replace S with a wildcard.
Using wildcards is clearer and more concise than declaring explicit type parameters,
and should therefore be preferred whenever possible.


Wildcards also have the advantage that they can be used outside of method signatures,
as the types of fields, local variables and arrays. Here is an example.

Returning to our shape drawing problem, suppose we want to keep a history of drawing requests.
We can maintain the history in a static variable inside class Shape,
and have drawAll() store its incoming argument into the history field.

static List<List<? extends Shape>>
    history = new ArrayList<List<? extends Shape>>();

public void drawAll(List<? extends Shape> shapes) {
    history.addLast(shapes);
    for (Shape s: shapes) {
        s.draw(this);
    }
}

*/

//public class Matter<? extends Number> {
//public class Matter<?> {
public class Matter<E extends Number> {
    private E eItem;
    private String instanceVariable;
    private E getInstanceTypeArgument(){
        E temporaryE;
        return this.eItem;
    }
    static <T> void printAnyTypeArray(List<T> anyArray){
        for(T element : anyArray){
            System.out.println("T element:"+element);
        }
    }
    static <T> void printAnySuperTypeArray(List<? extends T> anyArray){
        /*
        Notice that inside printAnySuperTypeArray(),
        we can still read elements from anyArray and give them type Object.
        This is always safe, since whatever the actual type of the collection,
        it does contain objects.
        */
        for(T element : anyArray){
            System.out.println("Object element:"+element);
            //anyArray.addToTail(element);
            //helperMethod(anyArray,element);
        }
        /*
        It isn't safe to addToTail arbitrary objects to it however.
        Since we don't know what the element type of anyArray stands for,
        we cannot addToTail objects to it.
        The addToTail() method takes parameters of type E,
        the element type of the collection.
        When the formal type parameter is ?,
        it stands for some unknown type.
        Any argument we pass to addToTail would have to be a subtype of this unknown type.
        Since we don't know what type that is,
        we cannot pass anything in.
        The sole exception is null, which is a member of every type.
        anyArray.addToTail(null);

        /*
        Can we use HELPER?
        //anyArray.addToTail(new Object());
        //helperMethod(anyArray,new Object());
        */
    }

    static <T> void helperMethod(List<T> tList, T tType){
        tList.add(tType);
    }



    //private static <? extends Number> void setItem (Collection<?> collection){
    //private static void setItem (Collection<?> collection, ? item){
    private static void doSmth (Collection<?> collection){
        //E zip;
    }

    private static void doanOther (Collection<? extends Number> collection){
        //E zip;
    }

    static  <T, E extends T> T writeAll(Collection<E> collection, Sink<? super T> operation){
        E lastSinked = null;
        for(E item : collection){
            lastSinked=item;
            operation.flush(item);
        }
        return lastSinked;
    }

    public static void main(String... args){

        Collection<String> strings = new ArrayList<>();

        strings.add("str1");
        strings.add("str2");
        strings.add("str3");
        strings.add("str4");
        strings.add("str5");

        Sink<Object> objectSink = new Sink<Object>() {
            @Override
            public void flush(Object o) {
                System.out.println("Flushing:" + o);
            }
        };

        String result = writeAll(strings,objectSink);
        System.out.println("RESULT->"+result);


        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.1);
        doubleList.add(2.2);

        List<String> stringList = new ArrayList<>();
        stringList.add("bir");
        stringList.add("iki");

        //printArrayExtends(doubleList,9.9);
        //printArraySuper(doubleList,9.9);
        //printArrayExtends(stringList,"bir");
        //printArraySuper(stringList,"iki");

        /*
        Wildcards also have the advantage that
        they can be used outside of method signatures,
        as the types of fields, local variables and arrays.
        Here is an example.
        */
        List<List<? extends Number>> history = new ArrayList<List<? extends Number>>();
    }

    interface Sink<T>{
        void flush(T t);
    }



}
