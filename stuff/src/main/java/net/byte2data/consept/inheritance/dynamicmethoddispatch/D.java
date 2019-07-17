package net.byte2data.consept.inheritance.dynamicmethoddispatch;

public class D extends A {
    public static String classVariable = "D Class Variable";
    public String instanceVariable = "D Instance Variable";

    public static void classMethod(){
        System.out.println("D - classMethod - instanceVariable:CANT BE REACHED  - classVariable:"+classVariable);
    }
    public void instanceMethod(){
        System.out.println("D - instanceMethod - instanceVariable:" + instanceVariable + " - classVariable:"+classVariable);
    }
}
