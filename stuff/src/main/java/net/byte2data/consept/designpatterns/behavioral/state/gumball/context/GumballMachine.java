package net.byte2data.consept.designpatterns.behavioral.state.gumball.context;

import net.byte2data.consept.designpatterns.behavioral.state.gumball.state.*;
import net.byte2data.consept.designpatterns.behavioral.state.gumball.state.intf.IState;

public class GumballMachine {

    /*
    TODO: Bütün state'ler define ediliyor!
    */
    private IState state;
    private IState noQuarterState;
    private IState hasQuarterState;
    private IState soldState;
    private IState soldOutState;
    private volatile int totalGumballCount;

    public GumballMachine(int initialCount){
        /*
        TODO: Define edilen tüm state'ler initialize ediliyor!
        */
        this.totalGumballCount =initialCount;
        this.noQuarterState =new NoQuarter(this);
        this.hasQuarterState =new HasQuarter(this);
        this.soldState =new Sold(this);
        this.soldOutState =new SoldOut(this);
        this.state= noQuarterState;
    }


    /*
    TODO: Belkide en önemli adam bu!
    */
    public void setState(IState state) {
        this.state = state;
    }


    /*
    TODO: Define edilen statelerin GET methodları var
    */
    public IState getNoQuarterState() {
        return noQuarterState;
    }
    public IState getHasQuarterState() {
        return hasQuarterState;
    }
    public IState getSoldState() {
        return soldState;
    }
    public IState getSoldOutState() {
        return soldOutState;
    }


    /*
    TODO: Class'ın asıl behaviour'ları o anda hangi state'de bulunuluyorsa onun tarafından yapılıyor.
    */
    public void insertQuarter() {
        this.state.insertQuarter();
    }
    public void ejectQuarter() {
        this.state.ejectQuarter();
    }
    public void turnCrank() {
        this.state.turnCrank();
        this.state.dispense();
    }


    /*
    TODO: State'lerden bağımsız methodlar da mevcut!
    */
    public synchronized int fillTheBox(int count){
        return totalGumballCount=totalGumballCount+count;
    }
    public synchronized int takeOffFromBox(int count){
        return totalGumballCount=totalGumballCount-count;
    }
    public int getCountInTheBox(){
        return this.totalGumballCount;
    }


}
