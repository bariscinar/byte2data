package net.byte2data.tutorial.generics.wildcard.wildcardcaptureandhelper;

import java.util.List;

/*
In some cases, the compiler infers the type of a wildcard.
For example, a list may be defined as List<?> but,
when evaluating an expression,
the compiler infers a particular type from the code.
This scenario is known as wildcard capture.
*/

public class Matter {
    void processList(List<?> anyList){
        /*
        In this example, the compiler processes the anyList input parameter as being of type Object.
        When the processList method invokes List.set(int, E),
        the compiler is not able to confirm the type of object that is being inserted into the list,
        and an error is produced.
        When this type of error occurs it typically means that,
        the compiler believes that you are assigning the wrong type to a variable.
        Generics were added to the Java language for this reason — to enforce type safety at compile time.
        */
        /*
        In this example, the code is attempting to perform a safe operation,
        so how can you work around the compiler error?
        You can fix it by writing a private helper method which captures the wildcard.
        In this case, you can work around the problem by creating the private helper method, helper!
        */
        //anyList.set(0,anyList.get(0));
        //anyList.addToTail(anyList.get(0));
        helper(anyList);
    }
    /*
    By convention, helper methods are generally named originalMethodNameHelper.
    */
    private <T> void helper(List<T> tList){
        tList.set(0, tList.get(0));
        tList.add(0, tList.get(0));
    }

    <T, S extends T> void processList(List<T> tList, S element){
        tList.set(0,element);
        //anyList.addToTail(anyList.get(0));
        //helper(anyList);
    }
}
