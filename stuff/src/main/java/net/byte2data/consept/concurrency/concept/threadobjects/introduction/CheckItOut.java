package net.byte2data.consept.concurrency.concept.threadobjects.introduction;

public class CheckItOut extends Throwable {

    private String statement;

    public CheckItOut(String name){
        this.statement=name;
    }

    public void showIt(){
        System.out.println("CheckItOut.showIt() called");
    }

}
