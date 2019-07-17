package net.byte2data.consept.designpatterns.behavioral.strategy.car;

import net.byte2data.consept.designpatterns.behavioral.strategy.car.context.Car;
import net.byte2data.consept.designpatterns.behavioral.strategy.car.context.impl.HatchBack;
import net.byte2data.consept.designpatterns.behavioral.strategy.car.strategies.impl.DiscBreak;
import net.byte2data.consept.designpatterns.behavioral.strategy.car.strategies.impl.HydraulicBreak;

public class CarTest {

    public static void main(String... args){
        HydraulicBreak hydraulicBreak = new HydraulicBreak();
        DiscBreak discBreak = new DiscBreak();
        Car aCar = new HatchBack(hydraulicBreak);
        aCar.applyBreak();
        aCar.setBreakStrategy(discBreak);
        aCar.applyBreak();
    }

}
