package net.byte2data.consept.designpatterns.structural.decorator.textbook.component.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.Beverage;

public class Coffee extends Beverage {

    public Coffee() {
        this.beverageDescription="CONCRETE COFFEE";
    }

    @Override
    public double getCost() {
        return 5.0;
    }

}
