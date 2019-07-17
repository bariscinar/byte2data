package net.byte2data.consept.serialization.usageoftransient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
Please notice that usage of "transient" with other keywords such as "final" and "static"
*/

public class WhatIsTransient implements Serializable {

    private Map<String, String> stringIntegerHashMap = new HashMap<>();

    private transient static String[] staticStringArray= {"1","2","3","4","5"};
    private transient static String[] anOtherStaticStringArray;
    private transient static String[] anOtherStaticStringArrayJustOnlyDefinition;
    /*
    If "static" modifier is added and changed this instance variable to class variable,
    JVM will ignore transient modifier and this member is serialized also.
    */
    private static transient String[] transientArray = {"null","null","null","null","null","null","null","null","null","null"};
    private transient String sysDate = "initial date";

    /*
    Please check the different type of variables and their behaviors in serialization!
    */
    private final static transient String finalStaticTransientString = "finalStaticTransientString";
    private static transient String staticTransientString = "staticTransientString";
    private final transient String finalTransientString = "finalTransientString";
    private transient String transientString = "transientString";

    private final static transient AReferenceObject finalStaticTransientReference = new AReferenceObject("finalStaticTransientReference");
    private static transient AReferenceObject staticTransientReference = new AReferenceObject("staticTransientReference");
    /*
    Normally final modifier makes an transient object is not passing through serialization process
    and ignored by JVM but for reference types final modifier itself does not affect transient behavior.
    */
    private final transient AReferenceObject finalTransientReference = new AReferenceObject("finalTransientReference");
    private transient AReferenceObject transientReference = new AReferenceObject("transientReference");

    static {
        anOtherStaticStringArray=new String[]{"6","7","8","9","0"};

    }

    public WhatIsTransient(){
        System.out.println((new SimpleDateFormat("dd-MM-yyyy HH:mm:ss SSS Z")).format(new Date()));
        staticStringArray= new String[]{"11","22","33","44","55"};
        anOtherStaticStringArrayJustOnlyDefinition = new String[]{"11","22","33","44","55"};
        stringIntegerHashMap.put("key1","value1");
        stringIntegerHashMap.put("key2","value2");
        stringIntegerHashMap.put("key3","value3");
        stringIntegerHashMap.put("key4","value4");
        stringIntegerHashMap.put("key5","value5");
    }

    public String[] getAll(){
        System.out.println(this.hashCode());
        this.sysDate = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss SSS Z")).format(new Date());
        this.transientString = "doldu";
        int test;
        if(null== transientArray){
            transientArray = new String[10];
        }
        int allIndex = 0;
        for(String item : staticStringArray)
            transientArray[allIndex++]=item;
        for(String otherItem : anOtherStaticStringArray)
            transientArray[allIndex++]=otherItem;
        return transientArray;
    }

    @Override
    public String toString() {
        return "WhatIsTransient{\n" +

                "staticStringArray=" + Arrays.toString(staticStringArray) + '\n' +
                "anOtherStaticStringArray=" + Arrays.toString(anOtherStaticStringArray) + '\n' +
                "anOtherStaticStringArrayJustOnlyDefinition=" + Arrays.toString(anOtherStaticStringArrayJustOnlyDefinition) + '\n' +

                "stringIntegerHashMap=" + stringIntegerHashMap + '\n' +

                "sysDate=" + sysDate + '\n' +
                "transientArray=" + Arrays.toString(transientArray) + '\n' +

                "finalStaticTransientString=" + finalStaticTransientString + '\n' +
                "staticTransientString=" + staticTransientString + '\n' +
                "finalTransientString=" + finalTransientString + '\n' +
                "transientString=" + transientString + '\n' +

                "finalStaticTransientReference=" + finalStaticTransientReference + '\n' +
                "staticTransientReference=" + staticTransientReference + '\n' +
                "finalTransientReference=" + finalTransientReference + '\n' +
                "transientReference=" + transientReference + '\n' +

                '}';
    }

    private static class Test {
        public static void main(String... args){
            try{
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/tmp/WhatIsTransient.ser"));
                WhatIsTransient whatIsTransient = new WhatIsTransient();
                objectOutputStream.writeObject(whatIsTransient);
                objectOutputStream.close();
                System.out.println("Serialization completed");

                Thread.sleep(1000);

                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/tmp/WhatIsTransient.ser"));
                System.out.println("Deserialization completed");
                Thread.sleep(1000);

                WhatIsTransient obj = (WhatIsTransient) objectInputStream.readObject();

                System.out.println("First call of instance");
                System.out.println(obj.toString());

                System.out.println("Second call of instance");
                obj.getAll();
                System.out.println(obj.toString());

            }catch (IOException | InterruptedException | ClassNotFoundException e){
                System.out.println("Exception: " + e);
            }
        }

    }
}
