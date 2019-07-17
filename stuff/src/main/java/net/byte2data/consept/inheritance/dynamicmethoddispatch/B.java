package net.byte2data.consept.inheritance.dynamicmethoddispatch;

public class B extends A {
    public static String classVariable = "B Class Variable";
    public String instanceVariable = "B Instance Variable";

    public static void classMethod(){
        System.out.println("B - classMethod - instanceVariable:CANT BE REACHED  - classVariable:"+classVariable);
    }
    public void instanceMethod(){
        System.out.println("B - instanceMethod - instanceVariable:" + instanceVariable + " - classVariable:"+classVariable);
    }
}
