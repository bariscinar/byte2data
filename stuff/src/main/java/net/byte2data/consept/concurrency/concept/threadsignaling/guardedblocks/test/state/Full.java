package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state;

import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.resource.Resource;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state.impl.IState;

public class Full implements IState {

    private Resource resource;
    public Full(Resource resource){
        this.resource=resource;
    }

    @Override
    public synchronized String getMessage() {
        this.resource.setState(resource.getEmptyState());
        //notify();
        return this.resource.getMessage();

    }

    @Override
    public synchronized void  putMessage(String msg) throws InterruptedException{
        wait();
        this.resource.setMessage(msg);
        this.resource.setState(resource.getFullState());
        notify();
    }
}
