package net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.impl;

import net.byte2data.consept.designpatterns.Constants;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.IBillingStrategy;

public class NormalBillingStrategy implements IBillingStrategy {

    @Override
    public double pay(double fee) {
        return (fee + (fee* Constants.TAX));
    }
}
