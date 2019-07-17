package net.byte2data.consept.designpatterns.behavioral.state.gumball.state;

import net.byte2data.consept.designpatterns.behavioral.state.gumball.context.GumballMachine;
import net.byte2data.consept.designpatterns.behavioral.state.gumball.state.intf.IState;

public class HasQuarter implements IState {

    private GumballMachine machine;
    public HasQuarter(GumballMachine gumballMachine){
        this.machine=gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("HasQuarter: Warning: InsertQuarter: quarter already inserted!");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("HasQuarter: EjectQuarter is completed and state will be changed as NoQuarter");
        this.machine.setState(machine.getNoQuarterState());

    }

    @Override
    public void turnCrank() {
        System.out.println("HasQuarter: TurnCrank is completed and state will be changed as Sold");
        this.machine.setState(machine.getSoldState());

    }

    @Override
    public void dispense() {
        System.out.println("HasQuarter: Warning: Dispense: Please turn crank first!");
    }
}
