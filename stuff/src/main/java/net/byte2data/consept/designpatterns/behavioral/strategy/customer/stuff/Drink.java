package net.byte2data.consept.designpatterns.behavioral.strategy.customer.stuff;

public class Drink {
    private String name;
    private double fee;

    public Drink(String name, double fee){
        this.name=name;
        this.fee=fee;
    }

    public double getFee() {
        return fee;
    }

}
