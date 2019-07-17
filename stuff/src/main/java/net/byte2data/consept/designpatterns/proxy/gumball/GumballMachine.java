package net.byte2data.consept.designpatterns.proxy.gumball;

import java.util.concurrent.atomic.AtomicInteger;

public class GumballMachine {

    private String location;
    private String state;
    private volatile int ballCount;
    private AtomicInteger atomicBallCount;

    public synchronized void decrease(int quantity){
        this.ballCount-=quantity;
        this.atomicBallCount.addAndGet(-quantity);
    }

    public int getBallCount(){
        return this.ballCount;
    }

    public GumballMachine(String location, int balls){
        this.location=location;
        this.ballCount=balls;
        this.atomicBallCount=new AtomicInteger(balls);
    }

    public String getLocation(){
        return this.location;
    }

}
