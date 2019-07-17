package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.box;

/**
 * Created by barisci on 26.09.2017.
 */
public class GenericBox<T,E> {
    private T instanceBox;
    private T instanceBox2;

    public <F>GenericBox(T t1){
        instanceBox=t1;
    }

    public GenericBox(E e1, T t2){
        instanceBox=t2;
    }

    public T getInstanceBox() {
        return instanceBox;
    }

    public void setInstanceBox(T instanceBox) {
        this.instanceBox = instanceBox;
    }
}
