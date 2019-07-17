package net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.context;

import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state.LowerState;
import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state.UpperState;
import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state.intf.IState;

public class Context {

    private IState state;
    private IState upper;
    private IState lower;

    public Context(){
        this.lower=new LowerState(this);
        this.upper=new UpperState(this);
        this.state=lower;
    }

    public IState getUpper() {
        return upper;
    }

    public IState getLower() {
        return lower;
    }

    public void setState(IState state){
        this.state=state;
    }

    public void write(String statement){
        this.state.write(statement);
    }

}
