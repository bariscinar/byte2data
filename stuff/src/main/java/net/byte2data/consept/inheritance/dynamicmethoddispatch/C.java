package net.byte2data.consept.inheritance.dynamicmethoddispatch;

public class C extends B {
    public static String classVariable = "C Class Variable";
    public String instanceVariable = "C Instance Variable";

    public static void classMethod(){
        System.out.println("C - classMethod - instanceVariable:CANT BE REACHED  - classVariable:"+classVariable);
    }
    public void instanceMethod(){
        System.out.println("C - instanceMethod - instanceVariable:" + instanceVariable + " - classVariable:"+classVariable);
    }
}
