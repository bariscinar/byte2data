package net.byte2data.consept.designpatterns.behavioral.state.gumball.state;

import net.byte2data.consept.designpatterns.behavioral.state.gumball.context.GumballMachine;
import net.byte2data.consept.designpatterns.behavioral.state.gumball.state.intf.IState;

public class Sold implements IState {

    private GumballMachine machine;
    public Sold(GumballMachine gumballMachine){
        this.machine=gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("Sold: InsertQuarter is completed and state is changed as HasQuarter");
        this.machine.setState(machine.getHasQuarterState());
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Sold: Warning: EjectQuarter: Insert quarter first!");
    }

    @Override
    public void turnCrank() {
        System.out.println("Sold: Warning: TurnCrank: Insert quarter first!");
    }

    @Override
    public void dispense() {
        System.out.println("Sold: Dispense is completed and state will be changed as NoQuarter or SoldOut depends on the quantity of the balls");
        this.machine.takeOffFromBox(1);
        if(machine.getCountInTheBox()>0) {
            System.out.println("Sold: Dispense is completed and state will is changed as NoQuarter");
            this.machine.setState(machine.getNoQuarterState());
        }else {
            System.out.println("Sold: Dispense is completed and state will is changed as SoldOut");
            this.machine.setState(machine.getSoldOutState());
        }
    }
}
