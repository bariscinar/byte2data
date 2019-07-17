package net.byte2data.consept.designpatterns.creational.factory.simple.book.test;

import net.byte2data.consept.designpatterns.creational.factory.simple.book.component.intf.ABook;
import net.byte2data.consept.designpatterns.creational.factory.simple.book.factories.BookFactory;
import net.byte2data.consept.designpatterns.creational.factory.simple.book.stuff.PressType;

public class BookTest {

    public static void main(String... args){

        ABook aBook = BookFactory.printBook("","",0, PressType.HARDCOPY);
        System.out.println(aBook.toString());
    }

}
