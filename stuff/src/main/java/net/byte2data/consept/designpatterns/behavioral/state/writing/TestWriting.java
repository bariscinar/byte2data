package net.byte2data.consept.designpatterns.behavioral.state.writing;

import net.byte2data.consept.designpatterns.behavioral.state.writing.withoutpattern.PatternlessContext;
import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.context.Context;
import net.byte2data.consept.designpatterns.behavioral.state.writing.withpattern.state.UpperState;

public class TestWriting {

    public static void main(String... args){
        PatternlessContext patternlessContext = new PatternlessContext(PatternlessContext.UpLow.LOWER);
        patternlessContext.writeStatement("NE OLUYOŞ LAYN İBNE!");
        patternlessContext.setState(PatternlessContext.UpLow.UPPER);
        patternlessContext.writeStatement("şişiğodöçsöd  dlmc");

        Context context = new Context();
        context.write("Monday");
        context.write("Tuesday");
        context.write("Wednesday");
        context.write("Thursday");
        context.write("Friday");
        context.write("Saturday");
        context.write("Sunday");

    }

}
