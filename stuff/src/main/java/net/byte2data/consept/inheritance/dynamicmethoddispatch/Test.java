package net.byte2data.consept.inheritance.dynamicmethoddispatch;

public class Test {

    public static void main(String... args){
        A prototype;
        //System.out.println(prototype.instanceVariable);
        //prototype.instanceMethod();
        System.out.println(A.classVariable);
        A.classMethod();

        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();

        //a.instanceMethod();
        //b.instanceMethod();
        //c.instanceMethod();
        //d.instanceMethod();
        System.out.println("---");

        prototype = a;
        System.out.println("instance member - Polymorphism: " + prototype.instanceVariable);
        System.out.println("class member - Polymorphism: " + prototype.classVariable);
        prototype.instanceMethod();
        prototype.classMethod();
        System.out.println("---");

        prototype = b;
        System.out.println("instance member - Polymorphism: " + prototype.instanceVariable);
        System.out.println("class member - Polymorphism: " + prototype.classVariable);
        prototype.instanceMethod();
        prototype.classMethod();
        System.out.println("---");

        prototype = c;
        System.out.println("instance member - Polymorphism: " + prototype.instanceVariable);
        System.out.println("class member - Polymorphism: " + prototype.classVariable);
        prototype.instanceMethod();
        prototype.classMethod();
        System.out.println("---");

        prototype = d;
        System.out.println("instance member - Polymorphism: " + prototype.instanceVariable);
        System.out.println("class member - Polymorphism: " + prototype.classVariable);
        prototype.instanceMethod();
        prototype.classMethod();
        System.out.println("---");
    }
}
