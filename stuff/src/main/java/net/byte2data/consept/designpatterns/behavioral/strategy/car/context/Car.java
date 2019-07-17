package net.byte2data.consept.designpatterns.behavioral.strategy.car.context;

import net.byte2data.consept.designpatterns.behavioral.strategy.car.strategies.IBreak;

public abstract class Car {

    //Check protected instance variable!
    protected IBreak breakStrategy;

    protected Car(IBreak breakStrategy){
        this.breakStrategy=breakStrategy;
    }

    //Check this final
    public void setBreakStrategy(final IBreak iBreak){
        this.breakStrategy = iBreak;
    }

    public void applyBreak(){
        breakStrategy.executeBreak();
    }

}
