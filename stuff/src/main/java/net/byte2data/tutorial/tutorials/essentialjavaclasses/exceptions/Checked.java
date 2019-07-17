package net.byte2data.tutorial.tutorials.essentialjavaclasses.exceptions;

import net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.nested.overall.Person;

/**
 * Created by barisci on 18.09.2017.
 */
public class Checked {
    private int instanceField = 12;

    private  int returnVal(int val, String str, Person p){
        Integer value = 0;
        try{
            instanceField=13;
            value=val * Integer.MAX_VALUE;
            value = 1;
            p.setAge(value);
            p.setName(str);
            System.out.println("returning from try - value:"+value);
            //return p;
            return value;
            //return instanceField;

        } catch (Error err){
            p.setName("err");
            value=-1;
            p.setAge(value);
            System.out.println("returning from error catch: " + err + " - value:" + value);
            return value;
        } catch (RuntimeException rte){
            value=-2;
            p.setName("rte");
            p.setAge(value);
            System.out.println("returning from runtime catch: " + rte+ " - value:" + value);
            return value;
        } catch (Exception exc){
            value=-3;
            p.setName("exc");
            p.setAge(value);
            System.out.println("returning from exception catch: " + exc+ " - value:" + value);
            return value;
        } finally{
            try{
                p.setName("returning from finally");
                instanceField=14;
                value=4;
                p.setAge(value);
                System.out.println("returning from finally - value:" + value);
                //return p;
                //return value;
                //return instanceField;
            }catch (Exception exc){
                value=5;
                p.setName("from inside finally catch");
                p.setAge(value);
                System.out.println("returning from inside finally catch: " + exc+ " - value:" + value);
                return value;
            }finally {
                value=6;
                System.out.println("returning from finally/finally - value:" + value);
                //return value;
            }
        }
        //System.out.println("value: " + value);
        //return p;
        //return value;
        //return instanceField;
    }
    public static void main(String... args){



        Person person1 = new Person("mike",11, Person.Sex.WOMAN);
        System.out.println("-----");
        //Person p = returnVal(88,"barış",person1);
        System.out.println("final value: " + new Checked().returnVal(88,"barış",person1));
        //System.out.println("person1 name: " + person1.getName());
        //System.out.println("person1 age: " + person1.getAge());
        //System.out.println("person1 sex: " + person1.getGender());
        //System.out.println("p name: " + p.getName());
        //System.out.println("p age: " + p.getAge());
        //System.out.println("p sex: " + p.getGender());
        System.out.println("-----");
    }
}
