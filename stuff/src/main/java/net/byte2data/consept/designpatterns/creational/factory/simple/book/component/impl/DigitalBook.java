package net.byte2data.consept.designpatterns.creational.factory.simple.book.component.impl;

import net.byte2data.consept.designpatterns.creational.factory.simple.book.stuff.PressType;
import net.byte2data.consept.designpatterns.creational.factory.simple.book.component.intf.ABook;

public class DigitalBook extends ABook{

    public DigitalBook(String name, String writer, int pageCount) {
        super(name, writer, pageCount, PressType.DIGITAL);
    }

    @Override
    public ABook createBook() {
        return this;
    }

    @Override
    public String toString() {
        return "DigitalBook: " + super.toString();
    }

}
