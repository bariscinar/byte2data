package net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.concrete;

import net.byte2data.consept.designpatterns.structural.decorator.BeverageSize;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.Beverage;

public class Tea extends Beverage {

    public Tea(BeverageSize beverageSize){
        this.beverageDescription="CONCRETE TEA";
        this.beverageCost=2.0D;
        this.beverageSize=beverageSize;
    }


}
