package net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state;

import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.context.Context;
import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state.intf.IState;

public class LowerState implements IState {

    private Context context;

    public LowerState(Context context){
        this.context=context;
    }

    @Override
    public void write(String statement) {
        System.out.println(statement.toLowerCase());
        this.context.setState(context.getUpper());
    }
}
