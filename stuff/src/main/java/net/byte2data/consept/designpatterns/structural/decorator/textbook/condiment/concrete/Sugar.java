package net.byte2data.consept.designpatterns.structural.decorator.textbook.condiment.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.Beverage;
import net.byte2data.consept.designpatterns.structural.decorator.textbook.condiment.Condiment;

public class Sugar extends Condiment {

    private Beverage beverage;

    public Sugar(Beverage beverage) {
        this.beverage=beverage;
        this.beverageDescription="CONDIMENT SUGAR";

    }

    @Override
    public String getDescription() {
        return this.beverage.getDescription() + ", " + this.beverageDescription;
    }

    @Override
    public double getCost() {
        return this.beverage.getCost()+0.1D;
    }
}
