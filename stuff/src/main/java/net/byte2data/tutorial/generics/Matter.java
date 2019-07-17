package net.byte2data.tutorial.generics;

import net.byte2data.consept.concurrency.GetDetail;

import java.util.*;

/*
    ***JAVA AUTOBOX
    ***REFLECTION

    Generics allow you to abstract over TYPES.
    By the way COLLECTIONS are called as CONTAINER TYPES.

    Much like the more familiar formal parameters used in method declarations,
    type parameters provide a way for you to re-use the same code with different inputs.
    The difference is that the inputs to formal parameters are values,
    while the inputs to type parameters are types.


    - Provides Stronger type checks at compile time.
    - Elimination of casts.
    - Enabling programmers to implement ***GENERIC ALGORITHMS
    By using generics, programmers can implement generic algorithms
    that work on collections of different types,
    can be customized, and are type safe and easier to read.
    Bounded type parameters are key to the implementation of generic algorithms.

    Formal Type Parameter-Variable <E>
    Actual Type Argument <Integer>

    Formal Value Parameter-Variable in the Method Definition - (String strParam)
    Formal Type Parameter-Variable in the Generic Method Definition - <E> (List<E> typeParam, E type)
    Actual Value Argument in the Method Call - (new String(""));
    Actual Type Argument in the Generic Method Call - (new ArrayList<Integer>, new Integer(0))

    Type Parameters are analogous to the ordinary parameters
    used in methods or constructors.
    Much like a method has Formal Value Parameters that describe,
    the kinds of values it operates on,
    a generic declaration has Formal Type Parameters.
    When a method is invoked,
    Actual (Value) Arguments are substituted for the Formal (Value) Parameters,
    and the method body is evaluated.
    When a Generic Declaration is invoked,
    the Actual Type Arguments are substituted for the Formal Type Parameters.

    The difference is that the inputs to Formal Value Parameters are Values(Arguments),
    while the inputs to Formal Type Parameters are Types (Arguments).

    **You can also substitute a Type Parameter (Formal Type Parameter) (i.e., K or V)
    with a Parameterized Type (Actual Type Argument) (i.e., List<String>) like,
    List<List<String>,String> = new ArrayList<>(new LinkedList<String>, new String("");

    In Java SE 7 and later,
    you can replace the type arguments required to invoke the constructor of a generic class,
    with an empty set of type arguments (<>) as long as the compiler can determine,
    or infer, the type arguments from the context.
    This pair of angle brackets, <>, is informally called the diamond.

    The compiler can check the type correctness of the program at compile-time.

    A generic type declaration is compiled once and for all,
    and turned into a single class file,
    just like an ordinary class or interface declaration.
    There aren't multiple copies of the code--not in source,
    not in binary, not on disk and not in memory.
    If you are a C++ programmer,
    you'll understand that this is very different than a C++ template.

    Generic Classes And Interfaces
    public interface List<T> extends Collection<T> {
        ...
    }

    Generic Methods And Constructors
    public static <T> T getFirst(List<T> list){
        ...
    }
*/

/*
RESTRICTIONS
- Cannot Instantiate Generic Types with Primitive Types
- Cannot Create Instances of Type Parameters
- Cannot Declare Static Fields Whose Types are Type Parameters
- Cannot Use Casts or instanceof With Parameterized Types
- Cannot Create Arrays of Parameterized Types

- Cannot Create, Catch, or Throw Objects of Parameterized Types
- Cannot Overload a Method Where the Formal Parameter Types
of Each Overload Erase to the Same Raw Type
*/

//@SuppressWarnings("unchecked")
//class name<T1, T2, ..., Tn> { /* ... */ }
public class Matter<E> {

    /*
    As consequence, the static variables and methods of a class are
    also shared among all the instances.
    Because there is only one copy per generic class at runtime,
    static variables are shared among all the instances of the class,
    regardless of their type parameter.

    Consequently, the type parameter cannot be used,
    in the declaration of static variables or in static methods.

    All instances of a generic class have the same run-time class,
    regardless of their actual type arguments.

    That is why it is illegal to refer to the type parameters of a type declaration,
    in a static method or initializer,
    or in the declaration or initializer of a static variable.

    private static E staticItem;
    private static E getStaticItem(){
        return staticItem;
    }
    */

    private E instanceTypeVariable;
    private List<E> instanceTypeList;
    /*
    There is no a way to define class type variable
    */
    //private static E classTypeVariable;
    //private static <T> T classTypeVariable;
    private static Object classVariable;

    private Matter(E initialItem){
        this.instanceTypeVariable =initialItem;
        classVariable =null;
        this.instanceTypeList = new ArrayList<>();
    }

    private  E getLatestItemInTheInternalList(){
        E zip;
        return instanceTypeList.get(instanceTypeList.size()-1);
    }

    private void pushItemIntoInternalList(E item){
        instanceTypeList.add(item);
    }

    private static <T> void setClassVariable(List<T> tList){
        classVariable =tList.get(0);
    }

    private static <T> void setItem (Collection<T> collection, T obj){
        //E zip;
        classVariable = obj;
        collection.add(obj);
    }

    static <E> E instantiateElementType(List<E> arg) throws IllegalAccessException, InstantiationException{
        //return new T(); //causes a compile error
        Class<?> tClass = arg.get(0).getClass();
        return (E)tClass.newInstance();
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

        Runtime'de bir stream içinden bazı değerler geliyorsa ve
        bunu bir parameterized list'e aktarıyorsak patlar mıyız?
        İyi ama bu soru herhangibir formal type alan method call için de geçerli değil mi?
        */

        /*
        Do not use RAW Types!
        */
        Matter rawMatter = new Matter("bir");
        Matter<Integer> integerMatter = new Matter<>(1);
        integerMatter.pushItemIntoInternalList(1);
        rawMatter.pushItemIntoInternalList("bir");
        integerMatter = rawMatter;
        rawMatter.pushItemIntoInternalList("iki");
        //By playing RAW TYPES even COMPILATION is succeeded, you would encounter RUN-TIME Exception
        //int intVal = integerMatter.getLatestItemInTheInternalList();
        //System.out.println("intVal:"+intVal);
        System.out.println("Get Latest:" + integerMatter.getLatestItemInTheInternalList());


        List <String> stringList = new ArrayList<String>();
        List<Integer> integerList = new ArrayList<Integer>();
        System.out.println("ArrayList<String>() classname:" + stringList.getClass().getName());
        System.out.println("ArrayList<Integer>() classname:" + integerList.getClass().getName());

        setItem(stringList,"onbir");
        System.out.println("classVariable:"+ classVariable);
        setItem(integerList,11);
        System.out.println("classVariable:"+ classVariable);
        /*
        Consequently, instantiating a Java class of a parameterized type is impossible,
        because instantiation requires a call to a constructor,
        which is unavailable if the type is unknown.
        For example, the following code cannot be compiled:
        <T> T instantiateElementType(List<T> arg) {
            return new T(); //causes a compile error
        }
        */

        List<? super Number> extendedNumbers = new ArrayList<>();
        extendedNumbers.add(1.1);
        List<? super String> extendedStrings = new ArrayList<>();
        extendedStrings.add("string");
        try{
             //System.out.println("instantiateElementType:" + instantiateElementType(extendedNumbers));
            System.out.println("instantiateElementType:" + instantiateElementType(extendedStrings));
        }catch (IllegalAccessException|InstantiationException ex){
            GetDetail.getThreadDetails("instantiateElementType exception:" + ex);
        }



        Matter<Double> doubleMatter = new Matter<>(1.1);
        System.out.println("classVariable:"+ classVariable);
        /*
        This feature (as shown below) known as type inference,
        allows you to invoke a generic method as an ordinary method,
        without specifying a type between angle brackets.
         */
        Matter.setClassVariable(stringList);
        System.out.println("classVariable:"+ classVariable);

    }



}
