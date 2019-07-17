package net.byte2data.consept.designpatterns.behavioral.state.gumball.state;

import net.byte2data.consept.designpatterns.behavioral.state.gumball.context.GumballMachine;
import net.byte2data.consept.designpatterns.behavioral.state.gumball.state.intf.IState;

public class SoldOut implements IState {

    private GumballMachine machine;
    public SoldOut(GumballMachine gumballMachine){
        this.machine=gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("SoldOut: Warning: InsertQuarter: refill the box first!");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("SoldOut: Warning: EjectQuarter: refill the box first!");
    }

    @Override
    public void turnCrank() {
        System.out.println("SoldOut: Warning: TurnCrank: refill the box first!");
    }

    @Override
    public void dispense() {
        System.out.println("SoldOut: Warning: Dispense: refill the box first!");
    }
}
