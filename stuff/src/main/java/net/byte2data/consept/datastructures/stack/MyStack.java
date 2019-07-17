package net.byte2data.consept.datastructures.stack;

import java.util.EmptyStackException;

public class MyStack<T> {

    private static class StackNode<T>{
        private T data;
        private StackNode<T> previousItem;

        public StackNode(T data){
            this.data=data;
            previousItem=null;
        }
    }

    private StackNode<T> topItem;

    public MyStack(){
        topItem=null;
    }

    public T pop(){
        if(null== topItem)
            throw new EmptyStackException();
        T item = topItem.data;
        topItem = topItem.previousItem;
        return item;
    }

    public void push(T item) {
        StackNode<T> newItem = new StackNode<T>(item);
        newItem.previousItem = topItem;
        topItem = newItem;
    }

    public T peek() {
        if (topItem == null) throw new EmptyStackException();
        return topItem.data;
    }

    public boolean isEmpty() {
        return topItem == null;

    }

    public static void main(String... args){
        MyStack<String> myStack = new MyStack<>();
        myStack.push("bir");
        myStack.push("iki");
        myStack.push("üç");

        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
    }

}
