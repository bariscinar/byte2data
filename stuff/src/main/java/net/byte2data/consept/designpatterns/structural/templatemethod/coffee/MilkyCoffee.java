package net.byte2data.consept.designpatterns.structural.templatemethod.coffee;

public class MilkyCoffee extends ACaffeineBeverage{

    public void brew(){
        System.out.println("milky coffee - brew");
    }

    public void addContiments(){
        System.out.println("milky coffee - addToTail some milk");
    }
}
