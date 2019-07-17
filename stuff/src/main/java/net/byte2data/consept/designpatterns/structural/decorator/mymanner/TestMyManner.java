package net.byte2data.consept.designpatterns.structural.decorator.mymanner;

import net.byte2data.consept.designpatterns.structural.decorator.BeverageSize;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.Beverage;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.concrete.Coffee;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.component.concrete.Tea;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.condiment.concrete.Milk;
import net.byte2data.consept.designpatterns.structural.decorator.mymanner.condiment.concrete.Sugar;

public class TestMyManner {

    public static void main(String... args){
        Beverage coffee = new Coffee(BeverageSize.LARGE);
        Beverage tea = new Tea(BeverageSize.SMALL);
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
