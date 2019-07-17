package net.byte2data.consept.designpatterns.behavioral.strategy.duck;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.FlyWithWings;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoFly;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.impl.NoQuack;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.impl.DecoyDuck;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.context.impl.RubberDuck;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;

public class DuckTest {

    public static void main(String... args){

        RubberDuck rubberDuck = new RubberDuck();
        rubberDuck.applyFly();
        rubberDuck.setFlyBehavior(new IFlyBehavior(){});
        rubberDuck.applyFly();
        rubberDuck.setFlyBehavior(new FlyWithWings());
        rubberDuck.applyFly();
        rubberDuck.setFlyBehavior(new NoFly());
        rubberDuck.applyFly();

        DecoyDuck decoyDuck = new DecoyDuck(new FlyWithWings(), new NoQuack());
        decoyDuck.applyFly();
        decoyDuck.display();
        decoyDuck.setFlyBehavior(new NoFly());
        decoyDuck.applyFly();


    }

}
