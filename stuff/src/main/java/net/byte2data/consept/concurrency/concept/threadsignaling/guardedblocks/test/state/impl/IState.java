package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state.impl;

public interface IState {
    public String getMessage() throws InterruptedException;
    public void putMessage(String msg) throws InterruptedException;
}
