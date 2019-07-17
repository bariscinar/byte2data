package net.byte2data.consept.designpatterns.structural.decorator;

public enum BeverageSize {
    LARGE(3), MEDIUM(2), SMALL(1);
    private int size;
    BeverageSize(int size){
        this.size=size;
    }

    public int getSize() {
        return size;
    }
}
