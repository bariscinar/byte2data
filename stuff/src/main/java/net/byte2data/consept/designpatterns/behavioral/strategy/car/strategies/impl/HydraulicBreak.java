package net.byte2data.consept.designpatterns.behavioral.strategy.car.strategies.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.car.strategies.IBreak;

public class HydraulicBreak implements IBreak {

    @Override
    public void executeBreak() {
        System.out.println("HydraulicBreak applied");
    }
}
