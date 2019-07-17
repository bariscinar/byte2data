package net.byte2data.consept.designpatterns.proxy.gumball;

public class TestGumballMonitor {

    public static void main(String... args){
        GumballMachine gumballMachine = new GumballMachine("cehennem",2);
        GumballMonitoring gumballMonitoring = new GumballMonitoring(gumballMachine);
        gumballMonitoring.monitor();
    }

}
