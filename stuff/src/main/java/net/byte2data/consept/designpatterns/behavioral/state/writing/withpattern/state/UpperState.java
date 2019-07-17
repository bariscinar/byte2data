package net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state;

import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.context.Context;
import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state.intf.IState;

public class UpperState implements IState {

    private Context context;
    private int count = 0;

    public UpperState(Context context){
        this.context=context;
    }

    @Override
    public void write(String statement) {
        System.out.println(statement.toUpperCase());
        if(++count>1)
        this.context.setState(context.getLower());
    }
}
