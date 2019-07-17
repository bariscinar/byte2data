package net.byte2data.consept.inheritance.dynamicmethoddispatch;

public class A {

    public static String classVariable = "A Class Variable";
    public String instanceVariable = "A Instance Variable";

    public static void classMethod(){
        System.out.println("A - classMethod - instanceVariable:CANT BE REACHED  - classVariable:"+classVariable);
    }
    public void instanceMethod(){
        System.out.println("A - instanceMethod - instanceVariable:" + instanceVariable + " - classVariable:"+classVariable);
    }

}
