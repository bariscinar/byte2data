package net.byte2data.consept.designpatterns.behavioral.state.enumsample;

public enum State {

    INITIAL {
        @Override
        State doSomething(String aParameter) {
            System.out.println("Doing Something in INITIAL state and jumping to NEXT_STEP, argument = " + aParameter);
            return NEXT_STEP;
        }
    },
    NEXT_STEP {
        @Override
        State doSomething(String aParameter) {
            System.out.println("Doing Something in NEXT_STEP and jumping into FINAL, argument = " + aParameter);
            return FINAL;
        }
    },
    FINAL {
        @Override
        State doSomething(String aParameter) {
            System.out.println("I am in FINAL state, argument = " + aParameter);
            return null;
        }
    };

    abstract State doSomething(String aParameter);
}
