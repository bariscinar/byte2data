package net.byte2data.consept.designpatterns.structural.decorator.drink.decorators;

import net.byte2data.consept.designpatterns.structural.decorator.drink.component.AbstractDrink;

public abstract class AContentDecorator extends AbstractDrink {

    private AbstractDrink mainContent;
    private double condimentPrice;
    private String condimentName;

    protected AContentDecorator(AbstractDrink acontent, double condimentPrice, String condimentName) {
        super(acontent.getName(),acontent.getPrice());
        this.mainContent=acontent;
        this.condimentPrice=condimentPrice;
        this.condimentName=condimentName;
    }

    @Override
    public String getName() {
        return super.getName() + " with " + this.condimentName;
    }

    @Override
    public double getPrice() {
        return super.getPrice()+this.condimentPrice;
    }
}
