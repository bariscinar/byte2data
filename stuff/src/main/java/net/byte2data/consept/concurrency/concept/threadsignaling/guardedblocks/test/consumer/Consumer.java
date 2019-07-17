package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.consumer;

import net.byte2data.consept.concurrency.GetDetail;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.resource.Resource;

public class Consumer implements Runnable{
    private Resource resource;
    private String consumedMessage;
    public Consumer(Resource rs){
        this.resource=rs;
    }
    public void run(){
        //do{
        GetDetail.getThreadDetails("Consumer.run is being starting");
        consumedMessage =resource.pullMessage();
        GetDetail.getThreadDetails("Consumer.run has completed");
        //}while(!consumedMessage.equalsIgnoreCase("100"));
    }
}