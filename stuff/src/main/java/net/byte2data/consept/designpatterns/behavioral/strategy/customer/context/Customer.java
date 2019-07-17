package net.byte2data.consept.designpatterns.behavioral.strategy.customer.context;

import net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.IBillingStrategy;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.stuff.Drink;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    private IBillingStrategy billingStrategy;

    //There is a FINAL here!
    public Customer(final IBillingStrategy billingStrategy){
        this.billingStrategy=billingStrategy;
    }

    //There is a FINAL here!
    public void setBillingStrategy(final IBillingStrategy billingStrategy){
        this.billingStrategy=billingStrategy;
    }

    public double applyPay(double fee){
        return billingStrategy.pay(fee);
    }

}
