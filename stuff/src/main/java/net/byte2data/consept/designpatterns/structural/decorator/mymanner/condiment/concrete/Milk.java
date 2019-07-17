package net.byte2data.consept.designpatterns.structural.decorator.mymanner.condiment.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.Beverage;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.condiment.Condiment;

public class Milk extends Condiment{

    protected Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage=beverage;
        this.beverageDescription="CONDIMENT MILK";
        this.beverageCost=0.5D;
        this.beverageSize=beverage.getBeverageSize();
    }

    @Override
    public String getDescription() {
        return this.beverageDescription + ", " + this.beverage.getDescription();
    }

    @Override
    public double getCost() {
        return this.beverageCost*beverage.getBeverageSize().getSize()+beverage.getCost();
    }
}
