package net.byte2data.tutorial.tutorials.learningthejavalanguage.langaugebasics.variables.arrays;

import java.lang.reflect.Field;

/**
 * Created by barisci on 27.08.2017.
 */
 public class ArrayCheck {
    private static int[] classIntArrayVariablePrivate;
    static int[] classIntArrayVariablePackagePrivate;
    protected static int[] classIntArrayVariableProtected;
    public static int[] classIntArrayVariablePublic;

    private int[] instanceIntArrayVariablePrivate;
    int[] instanceIntArrayVariablePackagePrivate;
    protected int[] instanceIntArrayVariableProtected;
    public int[] instanceIntArrayVariablePublic;


    public static void arrayOperation(int[] parameterArray){
        int var = 0;
        for(Field field : ArrayCheck.class.getFields()){
            String fieldName = field.getName();
            System.out.println("field name from static: " + fieldName);
            //System.arraycopy(parameterArray,var,field,var,++var+x1);
        }

    }

    public void arrayOperation(){
        int var = 0;
        for(Field field : ArrayCheck.class.getFields()){
            String fieldName = field.getName();
            System.out.println("field name from instance: " + fieldName);
        }
    }
}
