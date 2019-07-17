package net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.BeverageSize;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.Beverage;

public class Coffee extends Beverage {

    public Coffee(BeverageSize beverageSize) {
        this.beverageDescription="CONCRETE COFFEE";
        this.beverageCost=5.0D;
        this.beverageSize=beverageSize;
    }


}
