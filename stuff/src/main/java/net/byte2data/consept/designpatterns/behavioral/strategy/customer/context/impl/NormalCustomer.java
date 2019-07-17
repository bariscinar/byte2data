package net.byte2data.consept.designpatterns.behavioral.strategy.customer.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.IBillingStrategy;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.context.Customer;

public class NormalCustomer extends Customer {

    public NormalCustomer(IBillingStrategy billingStrategy) {
        super(billingStrategy);
    }

}
