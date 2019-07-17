package net.byte2data.consept.designpatterns.structural.decorator.textbook.component;

public abstract class Beverage {
    protected String beverageDescription = "BASE BEVERAGE";

    public String getDescription(){
        return this.beverageDescription;
    }
    public abstract double getCost();

}
