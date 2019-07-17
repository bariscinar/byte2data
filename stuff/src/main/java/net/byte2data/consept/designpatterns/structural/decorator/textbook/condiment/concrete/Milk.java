package net.byte2data.consept.designpatterns.structural.decorator.textbook.condiment.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.Beverage;
import net.byte2data.consept.designpatterns.structural.decorator.textbook.condiment.Condiment;

public class Milk extends Condiment {

    private Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage=beverage;
        this.beverageDescription="CONDIMENT MILK";

    }

    @Override
    public String getDescription() {
        return this.beverage.getDescription() + ", " + this.beverageDescription;
    }

    @Override
    public double getCost() {
        return this.beverage.getCost()+0.5D;
    }
}
