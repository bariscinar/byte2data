package net.byte2data.consept.designpatterns.behavioral.state.writing.withoutpattern;

public class PatternlessContext {

    public enum UpLow{
        UPPER, LOWER;
    }

    private UpLow state;

    public PatternlessContext(UpLow upLow){
        this.state=upLow;
    }

    public void setState(UpLow upLow){
        this.state=upLow;
    }

    public void writeStatement(String statement){
        if (this.state==UpLow.LOWER)
            System.out.println(statement.toLowerCase());
        else
            System.out.println(statement.toUpperCase());
    }

}
