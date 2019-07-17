package net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.resource;

import net.byte2data.consept.concurrency.GetDetail;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state.Empty;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state.Full;
import net.byte2data.consept.concurrency.concept.threadsignaling.guardedblocks.test.state.impl.IState;

public class Resource{

    private String message;

    //private boolean empty;
    IState state;
    IState fullState;
    IState emptyState;

    public Resource(){
        //empty=true;
        fullState=new Full(this);
        emptyState=new Empty(this);
        state=emptyState;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public IState getFullState() {
        return fullState;
    }

    public IState getEmptyState() {
        return emptyState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public synchronized void pushMessage(String message){
        try{
            /*
            while(!empty){
                GetDetail.getThreadDetails("pushMessage is waiting to put:"+message);
                wait();
                GetDetail.getThreadDetails("pushMessage is NOT waiting any moore to put:"+message);
            }
            this.message=message;
            empty=false;
            GetDetail.getThreadDetails("pushMessage is notifying to one of the threads for message:"+message);
            notify();
            GetDetail.getThreadDetails("pushMessage has notified to one of the other threads");
            */
            state.putMessage(message);
        }catch (InterruptedException ie){
            GetDetail.getThreadDetails("InterruptedException occurred in pushMessage: " + ie.getMessage());
        }

    }

    public synchronized String pullMessage(){
        try{
            /*
            while(empty){
                GetDetail.getThreadDetails("pullMessage - Bu noktada WAIT henuz cagrilmadi ve Drop objesinin intrinsic lock'u hala bu thread'in elinde olmalı!" +
                        "\nBurada 20 sn beklersem başka hiçbir hareket olmamalı! Oncelikle başka thread bu bloğa girememeli ve başka tradler bu objenin başka bloklarına da girememli!" +
                        "\n20 sn dolunca ve WAIT cağrılınca artık o lock geri verilmeli!");
                Thread.sleep(20000);
                GetDetail.getThreadDetails("pullMessage - WAIT BASLIYOR!");
                wait();
                GetDetail.getThreadDetails("pullMessage - WAIT BITTI!" +
                        "\nMuhtemelen wait çağrıldığında lock releas ediliyor olmalı!" +
                        "\nArtık başka threadler bu objenin intrinsic/monitor lock'unu alabilmeli ve bu blok olmasa da put bloğuna girebilmeli ama bu bloga yani get bloğuna hala kimse girememeli" +
                        "\nBurada 20 sn beklersem sadece get işini yapacak thradleri yavaşlatmış olacağım!");
                Thread.sleep(20000);
            }
            empty=true;
            GetDetail.getThreadDetails("pullMessage is notifying to other threads to get:"+this.message);
            notify();
            GetDetail.getThreadDetails("pullMessage has notified to other threads to get:"+this.message);
            return this.message;
            */
            return state.getMessage();
        }catch (InterruptedException ie){
            GetDetail.getThreadDetails("InterruptedException occurred in pullMessage: " + ie.getMessage());
        }
        return null;
    }

    public synchronized void justMix(){
        try{
            GetDetail.getThreadDetails("justMix is waiting to mix");
            Thread.sleep(2000);
            GetDetail.getThreadDetails("justMix is notifying to other threads");
            notify();
            GetDetail.getThreadDetails("justMix has notified to other threads");
        }catch (InterruptedException ie){
            GetDetail.getThreadDetails("InterruptedException occurred in pullMessage: " + ie.getMessage());
        }
    }
}