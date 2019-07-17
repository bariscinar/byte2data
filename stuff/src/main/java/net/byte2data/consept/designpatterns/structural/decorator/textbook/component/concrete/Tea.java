package net.byte2data.consept.designpatterns.structural.decorator.textbook.component.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.Beverage;

public class Tea extends Beverage {

    public Tea(){
        this.beverageDescription="CONCRETE TEA";
    }

    @Override
    public double getCost() {
        return 2.0;
    }
}
