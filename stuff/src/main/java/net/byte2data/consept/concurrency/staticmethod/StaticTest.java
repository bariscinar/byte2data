package net.byte2data.consept.concurrency.staticmethod;

import java.util.Random;

public class StaticTest {

    public static int getValue(Integer value){
        Integer newValue = new Integer(value);

        Integer otherValue = new Integer(value);
        return newValue + otherValue;
    }

    public static void concat(StringBuilder result,
                              StringBuilder sb,
                              StringBuilder sb1) {
        try{
            Thread.sleep((long)(Math.random()*100));
        }catch (InterruptedException ex){
            System.out.println("ex -> "+ex);
        }
        result.append(sb);
        result.append('|');
        result.append(sb1);
        System.out.println("-> " + result.toString());
    }

}
