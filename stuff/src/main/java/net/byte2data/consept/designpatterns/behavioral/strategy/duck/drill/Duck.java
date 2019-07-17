package net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill;

public abstract class Duck {

    public void quack(){
        System.out.println("Abstract - Quack");
    }

    public void swim(){
        System.out.println("Abstract - Swim");
    }

    /*
    public void fly(){
        System.out.println("Abstract - Fly");
    }
       */
    public abstract void display();

}
