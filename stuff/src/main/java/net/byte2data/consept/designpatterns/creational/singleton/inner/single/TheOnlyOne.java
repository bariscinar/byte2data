package net.byte2data.consept.designpatterns.creational.singleton.inner.single;

public class TheOnlyOne {

    private static class Inside{
        private static TheOnlyOne theOnlyOneInstance;
        private Inside(){
            if(null==theOnlyOneInstance)
                theOnlyOneInstance = new TheOnlyOne();
        }
    }

    private TheOnlyOne(){
    }

    public static TheOnlyOne getInstance(){
        return Inside.theOnlyOneInstance;
    }

}
