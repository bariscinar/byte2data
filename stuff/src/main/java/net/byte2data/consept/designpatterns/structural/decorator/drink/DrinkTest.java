package net.byte2data.consept.designpatterns.structural.decorator.drink;

import net.byte2data.consept.designpatterns.structural.decorator.drink.component.AbstractDrink;
import net.byte2data.consept.designpatterns.structural.decorator.drink.component.SimpleCoffee;
import net.byte2data.consept.designpatterns.structural.decorator.drink.decorators.MilkCondiment;
import net.byte2data.consept.designpatterns.structural.decorator.drink.decorators.SugarCondiment;

public class DrinkTest {

    public static void main(String... args){
        AbstractDrink content =
                new SugarCondiment(
                new MilkCondiment(
                new SimpleCoffee("Sade Türk Kahvesi", 5.00),
                        2.00D,"Yagsiz Sut"),
                        1.0D,"Esmer Şeker");
        System.out.println(content.getName());
        System.out.println(content.getPrice());
    }

}
