package net.byte2data.consept.designpatterns.creational.factory.simple.book.factories;

import net.byte2data.consept.designpatterns.creational.factory.simple.book.stuff.PressType;
import net.byte2data.consept.designpatterns.creational.factory.simple.book.component.impl.DigitalBook;
import net.byte2data.consept.designpatterns.creational.factory.simple.book.component.impl.HardBook;
import net.byte2data.consept.designpatterns.creational.factory.simple.book.component.intf.ABook;

public class BookFactory {
    public static ABook printBook(String name, String writer, int pageCount, PressType pressType){
        ABook aBook = null;
        if(pressType==PressType.DIGITAL){
            aBook=new DigitalBook(name,writer,pageCount);
        }else{
            aBook=new HardBook(name,writer,pageCount);
        }
        aBook=aBook.createBook();
        System.out.println(aBook.toString());
        return aBook.createBook();
    }
}
