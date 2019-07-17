package net.byte2data.consept.designpatterns.behavioral.state.gumball;

import net.byte2data.consept.designpatterns.behavioral.state.gumball.context.GumballMachine;

public class TestGumBall {

    public static void main(String... args){
        GumballMachine machine = new GumballMachine(10);
        System.out.println(machine.getCountInTheBox());
        System.out.println("----");
        machine.ejectQuarter();
        machine.turnCrank();
        System.out.println("----");
        machine.insertQuarter();
        machine.ejectQuarter();
        machine.turnCrank();
        System.out.println("----");
        machine.insertQuarter();
        machine.turnCrank();
        machine.ejectQuarter();
        System.out.println("----");
        System.out.println(machine.getCountInTheBox());
    }

}
