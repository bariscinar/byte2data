package net.byte2data.consept.designpatterns.behavioral.strategy.customer;

import net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.impl.HappyHourBillingStrategy;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.algorithms.impl.NormalBillingStrategy;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.context.Customer;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.context.impl.NormalCustomer;
import net.byte2data.consept.designpatterns.behavioral.strategy.customer.stuff.Drink;

public class CustomerTest {

    public static void main(String... args){
        Customer aCustomer = new NormalCustomer(new NormalBillingStrategy());
        System.out.println(aCustomer.applyPay(34));
    }

}
