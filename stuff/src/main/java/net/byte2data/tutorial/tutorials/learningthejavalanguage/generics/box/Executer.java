package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.box;

/**
 * Created by barisci on 26.09.2017.
 */
public class Executer {

    public static void main(String... args){
        NormalBox normalBox = new NormalBox();
        normalBox.setInstanceObj(12);
        normalBox.setInstanceObj("12");

        GenericBox<String,Integer> genericBox1 = new GenericBox<>("");
        GenericBox<Integer,NormalBox> genericBox2 = new GenericBox<Integer, NormalBox>(normalBox,1);
        genericBox1.setInstanceBox("12");

        OtherGenericBox otherGenericBox1 = new OtherGenericBox();
        otherGenericBox1.setT1("");
        otherGenericBox1.setT2(genericBox1);
        otherGenericBox1.setT3(normalBox);

        OtherGenericBox<String,NormalBox,GenericBox<Integer,String>> otherGenericBox2 = new OtherGenericBox();
        otherGenericBox2.setT1("");

        otherGenericBox2.setT2(normalBox);
        otherGenericBox2.getT2().getInstanceObj();

        otherGenericBox2.setT3(new GenericBox<Integer,String>("1",1));
        otherGenericBox2.getT3().getInstanceBox();
        System.out.println(otherGenericBox2.<Integer>getValue(new NormalBox()));

        Util.Pair<String, Integer> x = new Util.Pair<>("a",1);
        Util.Pair<String, Integer> y = new Util.Pair<>("a",1);

        System.out.println(Util.compare(x,y));
    }
}
