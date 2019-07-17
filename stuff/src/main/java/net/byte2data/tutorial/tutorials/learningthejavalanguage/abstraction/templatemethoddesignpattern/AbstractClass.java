package net.byte2data.tutorial.tutorials.learningthejavalanguage.abstraction.templatemethoddesignpattern;

public abstract class AbstractClass {
    public void process(){
        before();
        mid();
        after();
    }

    private void before(){
        System.out.format("%s%n","AbstractClass.before");
    }

    abstract void mid();

    private void after(){
        System.out.format("%s%n","AbstractClass.after");
    }
}
