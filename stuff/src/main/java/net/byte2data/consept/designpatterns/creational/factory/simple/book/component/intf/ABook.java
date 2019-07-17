package net.byte2data.consept.designpatterns.creational.factory.simple.book.component.intf;

import net.byte2data.consept.designpatterns.creational.factory.simple.book.stuff.PressType;

public abstract class ABook {

    private String writer;
    private String name;
    private int pageCount;
    private PressType pressType;

    public ABook(String name, String writer, int pageCount, PressType type){
        this.name=name;
        this.writer=writer;
        this.pageCount=pageCount;
        this.pressType=type;
    }

    @Override
    public String toString() {
        return "ABook{" +
                "writer='" + writer + '\'' +
                ", name='" + name + '\'' +
                ", pageCount=" + pageCount +
                ", pressType=" + pressType +
                '}';
    }

    public abstract ABook createBook();
}
