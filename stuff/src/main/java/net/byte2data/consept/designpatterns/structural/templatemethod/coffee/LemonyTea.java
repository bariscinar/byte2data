package net.byte2data.consept.designpatterns.structural.templatemethod.coffee;

public class LemonyTea extends ACaffeineBeverage{

    public void brew(){
        System.out.println("lemony tea - steep");
    }

    public void addContiments(){
        System.out.println("lemony tea - addToTail some lemon");
    }
}
