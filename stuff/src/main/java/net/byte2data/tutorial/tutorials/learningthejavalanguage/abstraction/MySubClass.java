package net.byte2data.tutorial.tutorials.learningthejavalanguage.abstraction;

public class MySubClass extends MyAbstractClass {
    @Override
    public void doSmthAbstract() {
        System.out.format("%s%n","impl doSmthAbstract");
    }

    @Override
    public void doSmth() {
        super.doSmth();
        System.out.format("%s%n","impl doSmth");
    }

    public static class Executer{
        public static void main(String... args){
            MySubClass mySubAbstract = new MySubClass();
            mySubAbstract.doSmth();
            mySubAbstract.doSmthAbstract();
        }
    }
}
