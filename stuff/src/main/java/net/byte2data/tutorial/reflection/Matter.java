package net.byte2data.tutorial.reflection;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
Reflection is commonly used by programs which require the ability
to examine or modify the runtime behavior of applications running in the Java virtual machine.

Pros
- Extensibility Features
An application may make use of external,
user-defined classes by creating instances of
extensibility objects using their fully-qualified names.
- Class Browsers and Visual Development Environments
- Debuggers and Test Tools

Cons
- Performance Overhead
Because reflection involves types that are dynamically resolved,
certain Java virtual machine optimizations can not be performed.
- Security Restrictions
Reflection requires a runtime permission which may
not be present when running under a security manager.
- Exposure of Internals
Since reflection allows code to perform operations
that would be illegal in non-reflective code,
such as accessing private fields and methods,
the use of reflection can result in unexpected side-effects,
which may render code dysfunctional and may destroy portability.
Reflective code breaks abstractions and therefore
may change behavior with upgrades of the platform.

Every type is either a reference or a primitive.
For every type of object, the Java virtual machine instantiates
an immutable instance of java.lang.Class which provides methods
to examine the runtime properties of the object including its members
and type information.
Class also provides the ability to create new classes and objects.

The entry point for all reflection operations is java.lang.Class.
With the exception of java.lang.reflect.ReflectPermission,
none of the classes in java.lang.reflect have public constructors.
To get to these classes, it is necessary to invoke appropriate methods on Class.
There are several ways to get a Class
depending on whether the code has access to an object,
the name of class, a type, or an existing Class.

If an instance of an object is available,
then the simplest way to get its Class is to invoke Object.getClass().
Of course, this only works for reference types which all inherit from Object.

If the type is available but there is no instance then it is possible to obtain a Class
by appending ".class" to the name of the type.
This is also the easiest way to obtain the Class for a primitive type.

If the fully-qualified name of a class is available,
it is possible to get the corresponding Class using the static method Class.forName().
This cannot be used for primitive types.
The syntax for names of array classes is described by Class.getName().
This syntax is applicable to references and primitive types.

The .class syntax is a more convenient and the preferred way
to obtain the Class for a primitive type;
however there is another way to acquire the Class.
Each of the primitive types and void has a wrapper class in java.lang
that is used for boxing of primitive types to reference types.
Each wrapper class contains a field named TYPE
which is equal to the Class for the primitive type being wrapped.
*/
interface MatterInterface{
}

public class Matter implements MatterInterface{
    public int test;
    public interface MemberInnerInterface<T>{
    }

    public class MemberInnerClass<T> implements MemberInnerInterface<T>{
        public Class kilas;
        public T type;
        public int instanceIntVariable;
        public Integer instangeIntegerVariable;
        /*
        MemberInnerClass() throws Exception{
            this.instanceIntVariable=0;
            this.instangeIntegerVariable=0;
            this.type=(T)type.getClass().newInstance();
            kilas=this.getClass();

        }
        */

        MemberInnerClass(int a, Integer b, T t){
            this.instanceIntVariable=a;
            this.instangeIntegerVariable=b;
            this.type=t;
            kilas=this.getClass();
            System.out.println("kilas.getName():" + kilas.getName());
            for(Field field : kilas.getFields()){
                System.out.println("field:"+field.getName());
            }
        }

    }
    private enum MyEnum{
        FAR,CLOSE;

    }
    public static void main(String... args) throws Exception{
        System.out.println("Reference String class ---");
        System.out.println("String.class:" + String.class);
        String stringInstance = new String();
        System.out.println("stringInstance.getClass():" + stringInstance.getClass());

        System.out.println("Primitive byte ---");
        System.out.println("byte.class:" + byte.class);
        byte byteInstance1 = 12;
        //System.out.println("byteInstance.getClass():" + byteInstance1.getClass());
        System.out.println("byteInstance.getClass() is not allowed*");

        System.out.println("Primitive byte Array* ---");
        System.out.println("byte[].class:" + byte[].class);
        byte[] byteInstance2 = new byte[1024];
        System.out.println("byte[]Instance.getClass():" + byteInstance2.getClass());
        System.out.println("Primitive int MultiDimensionArray* ---");
        int[][][] multiDimensionIntArray = new int[0][0][0];
        System.out.println("multiDimension[][][]IntArray.getClass():"+multiDimensionIntArray.getClass());
        System.out.println("int[][][] class():"+int[][][].class);

        System.out.println("---");
        System.out.println("Enum.class:" + Enum.class);
        System.out.println("MyEnum.class:" + MyEnum.class);
        System.out.println("MyEnum.FAR.getClass():" + MyEnum.FAR.getClass());


        System.out.println("--- Reference List interface");
        System.out.println("List.class:" + List.class);
        List<? extends Number> numbers = new ArrayList<>();
        System.out.println("numbers.getClass():" + numbers.getClass());

        System.out.println("--- Class.forName [[D");
        System.out.println(Class.forName("[[D").toString());

        System.out.println("--- Class.forName reflection.Matter$MemberInnerClass");
        try{
            System.out.println(Class.forName("net.byte2data.tutorial.reflection.Matter$MemberInnerClass").getName());
        }catch (Exception ex){
            System.out.println("Exception: " + ex);
        }

        System.out.println("--- Reference Matter class");
        System.out.println("Matter.class:" + Matter.class);

        Matter matterInstance = new Matter();
        System.out.println("matterInstance.getClass():" + matterInstance.getClass());

        System.out.println("--- Reference MemberInnerClass inner class");
        System.out.println("MemberInnerClass.class:" + MemberInnerClass.class);

        //MemberInnerClass memberInnerClassInstance1 = matterInstance.new MemberInnerClass();
        MemberInnerClass memberInnerClassInstance1 = matterInstance.new MemberInnerClass(1,1, 1.1d);
        System.out.println("memberInnerClassInstance1.getClass():" + memberInnerClassInstance1.getClass());
        System.out.println("memberInnerClassInstance1.type.getClass():" + memberInnerClassInstance1.type.getClass());

        //MemberInnerClass<String> memberInnerClassInstance2 = matterInstance.new MemberInnerClass();
        MemberInnerClass<String> memberInnerClassInstance2 = matterInstance.new MemberInnerClass(1,1, "test");
        System.out.println("memberInnerClassInstance2.getClass():" + memberInnerClassInstance2.getClass());
        System.out.println("memberInnerClassInstance2.type.getClass():" + memberInnerClassInstance2.type.getClass());

        System.out.println("--- Class.forName Samples");
        MatterInterface matterInterface = (MatterInterface) Class.forName(Matter.class.getName()).newInstance();
        System.out.println("matterInterface.getClass():" + matterInterface.getClass());
        for(Field field : matterInterface.getClass().getFields()){
            System.out.println("field of matterInterface: " + field.getName());
        }

        System.out.println("String.class:" + String.class);
        String stringObject = (String) Class.forName(String.class.getName()).newInstance();

        System.out.println("double[].class:" + double[].class);
        double[] doubleArray;
        Class cDoubleArray = Class.forName("[D");
        for(Method method : cDoubleArray.getMethods()){
            System.out.println("method of [D: " + method.getName());
        }


        System.out.println("String[][].class:" + String[][].class);
        String[][] stringMultiArray;
        Class cStringArray = Class.forName("[[Ljava.lang.String;");
        for(Method method : cStringArray.getMethods()){
            System.out.println("method of [[Ljava.lang.String;: " + method.getName());
        }


        Class cMatterArray = Class.forName("[[Lreflection.Matter;");
        for(Method method : cMatterArray.getMethods()){
            System.out.println("method of [[Lreflection.Matter;: " + method.getName());
        }



        /*
        MemberInnerInterface<String> stringMemberInnerInterface = (MemberInnerInterface) Class.forName(memberInnerClassInstance1.getClass().getName()).newInstance();
        System.out.println("stringMemberInnerInterface.getClass():" + stringMemberInnerInterface.getClass());
        System.out.println("---");
        */

    }
}
