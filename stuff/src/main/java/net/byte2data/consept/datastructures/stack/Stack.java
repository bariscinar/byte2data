package net.byte2data.consept.datastructures.stack;

import java.util.Random;

public class Stack {

    private String name;
    private int maxLengthOfStack;
    private Integer[] integerStack;
    private int currentIndexOfStack;


    public Stack(int sizeOfStack, String name){
        this.name=name;
        if(sizeOfStack>0) {
            maxLengthOfStack = sizeOfStack;
            integerStack = new Integer[maxLengthOfStack];
            currentIndexOfStack = -1;
        }else{
            System.out.println("Construction - There should be more appropriate way not to construct an object if it does not meet some preconditions!");
            System.exit(2);
        }
    }

    public void push(Integer item){
        if(currentIndexOfStack<maxLengthOfStack-1){
            integerStack[++currentIndexOfStack]=item;
            System.out.println("Putting: " + item);
        }else{
            System.out.println("Stack is full");
        }
    }

    public Integer pop(){
        Integer item = null;
        if(currentIndexOfStack<0) {
            System.out.println("Stack is empty");
        }else{
            item = integerStack[currentIndexOfStack--];
        }
        System.out.println("Popping: " + item);
        return item;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this.name + " is finalizing!");
    }

    public boolean equals(Stack obj) {
        return super.equals(obj);
    }

    public static class StackTest{
        public static void main(String... args){
            Integer sizeOfStack = new Random().nextInt(5);
            //Integer testCount = new Random().nextInt(50);
            System.out.println("Size Of Stack: " + sizeOfStack);
            //System.out.println("Test Count: " + testCount);
            Stack stack = new Stack(sizeOfStack, "test");
            short index = -1;
            while(++index > -32768)
                //for(int index=0; index<testCount; index++){
                if (index % ((new Random().nextInt(3)) + 1) == 0) {
                    stack.pop();
                } else {
                    stack.push((int)index);

                }
                //}
            System.out.println(index);
        }
    }

}
