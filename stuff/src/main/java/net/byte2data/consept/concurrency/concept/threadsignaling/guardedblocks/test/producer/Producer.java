package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.producer;

import net.byte2data.consept.concurrency.GetDetail;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.resource.Resource;

import java.util.Random;

public class Producer implements Runnable{
    private Resource resource;
    public Producer(Resource rs){
        this.resource=rs;
    }
    private String getAStatement(){
        return String.valueOf(new Random().nextInt(99));
    }
    public void run(){

        //for(int index=0; index<100; index++){
        GetDetail.getThreadDetails("Producer.run is being starting");
        resource.pushMessage(getAStatement());
        GetDetail.getThreadDetails("Producer.run has completed");
        //}
        //GetDetail.getThreadDetails("Producer.run is being starting");
        //resource.pushMessage("100");
        //GetDetail.getThreadDetails("Producer.run has completed");

    }
}
