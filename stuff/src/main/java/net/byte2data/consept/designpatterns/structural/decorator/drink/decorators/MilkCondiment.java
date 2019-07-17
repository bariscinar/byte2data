package net.byte2data.consept.designpatterns.structural.decorator.drink.decorators;

import net.byte2data.consept.designpatterns.structural.decorator.drink.component.AbstractDrink;

public class MilkCondiment extends AContentDecorator {

    public MilkCondiment(AbstractDrink acontent, double condimentPrice, String condimentName) {
        super(acontent, condimentPrice, condimentName);
    }

}
