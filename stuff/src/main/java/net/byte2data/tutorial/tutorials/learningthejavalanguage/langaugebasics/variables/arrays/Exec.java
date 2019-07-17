package net.byte2data.tutorial.tutorials.learningthejavalanguage.langaugebasics.variables.arrays;

/**
 * Created by barilsci on 27.08.2017.
 */
public class Exec extends ArrayCheck{

    public static void main(String... parameters){
        int[] localIntArrayVariable1 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        ArrayCheck arrayCheck = new ArrayCheck();
        ArrayCheck.arrayOperation(localIntArrayVariable1);
        arrayCheck.arrayOperation();
        System.out.println("--");
        Exec exec = new Exec();
        Exec.arrayOperation(localIntArrayVariable1);
        exec.arrayOperation();


    }

}
