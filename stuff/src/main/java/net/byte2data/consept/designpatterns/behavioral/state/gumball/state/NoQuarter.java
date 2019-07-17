package net.byte2data.consept.designpatterns.behavioral.state.gumball.state;

import net.byte2data.consept.designpatterns.behavioral.state.gumball.context.GumballMachine;
import net.byte2data.consept.designpatterns.behavioral.state.gumball.state.intf.IState;

public class NoQuarter implements IState {

    private GumballMachine machine;
    public NoQuarter(GumballMachine gumballMachine){
        this.machine=gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("NoQuarter: InsertQuarter is completed and state is changed as HasQuarter");
        this.machine.setState(machine.getHasQuarterState());

    }

    @Override
    public void ejectQuarter() {
        System.out.println("NoQuarter: Warning: EjectQuarter: Insert quarter first!");

    }

    @Override
    public void turnCrank() {
        System.out.println("NoQuarter: Warning: TurnCrank: Insert quarter first!");

    }

    @Override
    public void dispense() {
        System.out.println("NoQuarter: Warning: Dispense: Insert quarter first!");

    }
}
