package net.byte2data.consept.designpatterns.proxy.gumball;

public class GumballMonitoring {
    private GumballMachine gumballMachine;
    public GumballMonitoring(GumballMachine gumballMachine){
        this.gumballMachine=gumballMachine;
    }
    public void monitor(){
        System.out.println(this.gumballMachine.getLocation());
    }
}
