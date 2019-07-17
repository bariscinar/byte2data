package net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.concrete.MallardDuck;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.concrete.RedheadDuck;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.drill.concrete.RubberDuck;

public class Test {

    public static void main(String... args){
        Duck mallardDuck = new MallardDuck();
        Duck redheadDuck = new RedheadDuck();
        Duck rubberDuck = new RubberDuck();

        mallardDuck.display();
        //mallardDuck.fly();

        redheadDuck.display();
        //redheadDuck.fly();

        rubberDuck.display();
        //rubberDuck.fly();
    }

}
