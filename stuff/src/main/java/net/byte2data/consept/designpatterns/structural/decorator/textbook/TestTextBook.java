package net.byte2data.consept.designpatterns.structural.decorator.textbook;

import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.Beverage;
import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.concrete.Coffee;
import net.byte2data.consept.designpatterns.structural.decorator.textbook.component.concrete.Tea;
import net.byte2data.consept.designpatterns.structural.decorator.textbook.condiment.concrete.Milk;
import net.byte2data.consept.designpatterns.structural.decorator.textbook.condiment.concrete.Sugar;

public class TestTextBook {

    public static void main(String... args){
        Beverage coffee = new Coffee();
        Beverage tea = new Tea();
        System.out.println(coffee.getDescription() + " : " + coffee.getCost());
        System.out.println(tea.getDescription() + " : " + tea.getCost());

        Beverage coffeeWithMilk = new Milk(coffee);
        Beverage teaWithMilk = new Milk(tea);
        Beverage teaWithDoubleMilk = new Milk(teaWithMilk);
        System.out.println(coffeeWithMilk.getDescription() + " : " + coffeeWithMilk.getCost());
        System.out.println(teaWithDoubleMilk.getDescription() + " : " + teaWithDoubleMilk.getCost());

        Beverage coffeeWithMilkAndSugar = new Sugar(coffeeWithMilk);
        Beverage teaWithDoubleMilkAndSugar = new Sugar(teaWithDoubleMilk);
        System.out.println(coffeeWithMilkAndSugar.getDescription() + " : " + coffeeWithMilkAndSugar.getCost());
        System.out.println(teaWithDoubleMilkAndSugar.getDescription() + " : " + teaWithDoubleMilkAndSugar.getCost());
    }

}
