package net.byte2data.tutorial.generics.generatinginstanceoftype;

import java.util.ArrayList;
import java.util.List;

public class GenerateAnInstanceOfTypeParameter{

    public class GenericFactory <T> {
        private Class klas;
        public GenericFactory(Class cl){
            this.klas=cl;
        }
        public T createInstance(){
            try{
                return (T) klas.newInstance();
            }catch (InstantiationException|IllegalAccessException ex){
                System.out.println("Exception in creating a new instance:"+ex.getMessage());
            }
            return null;
        }
    }

    public class Box{
    }

    private <T> List<T> generateBulkList(Class<T> aClass, int count){
        List<T> tList = new ArrayList<>();
        GenericFactory<T> genericFactory = new GenericFactory<>(aClass);
        for (int index=0; index<count; index++){
            tList.add(genericFactory.createInstance());
        }
        return tList;
    }

    public void test(){
        for(Box box : generateBulkList(Box.class,10)){
            System.out.println("box:"+box);
        }

        for(String string : generateBulkList(String.class,10)){
            System.out.println("string:"+string);
        }
    }

    public static <E> void append(List<E> list, Class<E> cls) throws Exception {
        E elem = cls.newInstance();   // OK
        list.add(elem);
    }

    public static void main(String... args) throws Exception{
        GenerateAnInstanceOfTypeParameter intancing = new GenerateAnInstanceOfTypeParameter();
        intancing.test();
        append(new ArrayList<String>(),String.class);
    }
}
