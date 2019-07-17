package net.byte2data.tutorial.tutorials.learningthejavalanguage.abstraction.templatemethoddesignpattern;

public class ImplClass extends AbstractClass {

    @Override
    void mid() {
        System.out.format("%s%n","ImplClass.mid");
    }

    public static class Executer{
        public static void main(String... args){
            ImplClass implClass = new ImplClass();
            implClass.process();
        }
    }
}
