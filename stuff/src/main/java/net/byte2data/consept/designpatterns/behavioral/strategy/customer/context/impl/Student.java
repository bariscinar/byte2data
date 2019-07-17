package net.byte2data.consept.designpatterns.behavioral.strategy.customer.context.impl;

import net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.IBillingStrategy;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.context.Customer;

public class Student extends Customer {

    public Student(IBillingStrategy billingStrategy) {
        super(billingStrategy);
    }


}
