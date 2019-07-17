package net.byte2data.consept.designpatterns.structural.decorator.mymanner.component;

import net.byte2data.consept.designpatterns.structural.decorator.BeverageSize;

public abstract class Beverage {
    protected String beverageDescription;
    protected double beverageCost;
    protected BeverageSize beverageSize;

    /*
    TODO: Bu noktaya normalde aşağıdaki gibi bir constructor koyma alışkanlığım vardı!
    Bunun doğru olup olmadığını bilmiyorum!
    Yani abstract bir class içine constructor koymak doğru gelmiyor artık!
    Biraz araştır!!!
    */
    /*
    protected Beverage(){
        this.beverageDescription="BEVERAGE";
        this.beverageCost=0.0D;
        this.beverageSize=BeverageSize.LARGE;
    }
    */

    public String getDescription(){
        return this.beverageDescription;
    }

    public double getCost(){
        return (this.beverageCost*this.beverageSize.getSize());
    }

    public BeverageSize getBeverageSize() {
        return beverageSize;
    }
}
