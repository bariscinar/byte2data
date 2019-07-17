package net.byte2data.consept.designpatterns.behavioral.state.enumsample;

public class StatefulObject {

    private State state;

    public StatefulObject() {
        state = State.INITIAL;
    }

    public void performRequest(String aParameter) {
        state = state.doSomething(aParameter);
    }

    public static void main(String[] args) {
        StatefulObject theObject = new StatefulObject();
        theObject.performRequest("Hello");
        theObject.performRequest("Hello");
        theObject.performRequest("Hello");
        theObject.performRequest("Hello");
        theObject.performRequest("Hello");
    }
}
