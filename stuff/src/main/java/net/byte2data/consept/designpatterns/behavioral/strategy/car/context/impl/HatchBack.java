package net.byte2data.consept.designpatterns.behavioral.strategy.car.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.car.context.Car;
import net.byte2data.consept.designpatterns.behavioral.strategy.car.strategies.IBreak;

public class HatchBack extends Car {

    public HatchBack(IBreak iBreak){
        super(iBreak);
    }
}
