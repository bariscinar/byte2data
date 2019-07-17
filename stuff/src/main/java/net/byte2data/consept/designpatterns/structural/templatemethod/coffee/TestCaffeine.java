package net.byte2data.consept.designpatterns.structural.templatemethod.coffee;

public class TestCaffeine {

    public static void main(String... args){
        ACaffeineBeverage aCaffeineBeverage = new LemonyTea();
        aCaffeineBeverage.prepareRecipe();

        aCaffeineBeverage = new MilkyCoffee();
        aCaffeineBeverage.prepareRecipe();

        aCaffeineBeverage = new BlackOrMilkyCoffee();
        aCaffeineBeverage.prepareRecipe();
    }

}
