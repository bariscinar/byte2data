package net.byte2data.consept.designpatterns.structural.decorator.drink.decorators;

import net.byte2data.consept.designpatterns.structural.decorator.drink.component.AbstractDrink;

public class SugarCondiment extends AContentDecorator {

    public SugarCondiment(AbstractDrink acontent, double condimentPrice, String condimentName) {
        super(acontent, condimentPrice, condimentName);
    }


    @Override
    public double getPrice() {
        return super.getPrice();
    }
}
