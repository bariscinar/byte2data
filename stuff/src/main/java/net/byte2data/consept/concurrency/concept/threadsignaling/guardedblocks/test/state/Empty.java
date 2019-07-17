package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state;

import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.resource.Resource;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state.impl.IState;

public class Empty implements IState {

    private Resource resource;
    public Empty(Resource resource){
        this.resource=resource;
    }

    @Override
    public synchronized String getMessage() throws InterruptedException{
        wait();
        this.resource.setState(resource.getEmptyState());
        notify();
        return this.resource.getMessage();
    }

    @Override
    public void putMessage(String msg) {
        this.resource.setMessage(msg);
        this.resource.setState(resource.getFullState());
        //notify();
    }
}
