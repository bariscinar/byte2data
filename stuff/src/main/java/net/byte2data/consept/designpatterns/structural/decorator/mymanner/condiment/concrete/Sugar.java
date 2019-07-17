package net.byte2data.consept.designpatterns.structural.decorator.mymanner.condiment.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.Beverage;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.condiment.Condiment;

public class Sugar extends Condiment {
    protected Beverage beverage;
    public Sugar(Beverage beverage) {
        this.beverage=beverage;
        this.beverageDescription="CONDIMENT SUGAR";
        this.beverageCost=0.1D;
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
