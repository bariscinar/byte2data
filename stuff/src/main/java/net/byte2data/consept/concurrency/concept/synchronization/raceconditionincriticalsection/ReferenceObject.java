package net.byte2data.consept.concurrency.concept.synchronization.raceconditionincriticalsection;

public class ReferenceObject {
    private String internalState = "internalstate";
    public void setValue(String val){
        this.internalState=val;
    }
    public String getValue(){
        return this.internalState;
    }
}
