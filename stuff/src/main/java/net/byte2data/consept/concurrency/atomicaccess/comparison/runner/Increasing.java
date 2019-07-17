package net.byte2data.consept.concurrency.atomicaccess.comparison.runner;

import net.byte2data.consept.concurrency.atomicaccess.comparison.resource.Resource;

public class Increasing implements Runnable{

    private Resource localResource;

    public Increasing(Resource resource){
        this.localResource=resource;
    }

    @Override
    public void run() {
        try {
            localResource.instanceAtomicIncreament();
            localResource.nonSynchInstanceIncreament();
            localResource.nonSynchVolatileInstanceIncreament();
            //Resource.classIncreament();
            //Resource.synchClassIncreament();
        }catch (Exception ex){
            System.out.println("exception: " + ex);
        }
    }

}
