package net.byte2data.tutorial.tutorials.learningthejavalanguage.abstraction;

public abstract class MySubAbstractClass extends MyAbstractClass {
    @Override
    public void doSmthAbstract() {
        System.out.format("%s%n","MySubAbstractClass.doSmthAbstract");
    }

    @Override
    public void doSmth() {
        System.out.format("%s%n","MySubAbstractClass.doSmth");
    }
}
