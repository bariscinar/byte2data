package net.byte2data.consept.orm.utilities;

public class NullCheck<T> {
    private T instance;
    public NullCheck(T tInstance){
        this.instance=tInstance;
    }
    public boolean isNull(){
        return null==instance;
    }
}
