package net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.nested.overall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barisci on 18.09.2017.
 */
public class Person {
    private String name;
    private int age;
    private Sex sex;
    private String emailAddress;

    public enum Sex{
        BOY("çocuk",18),
        GIRL("kız",18),
        MALE("adam",999),
        WOMAN("kadın",999);

        private int age;
        private String folk;
        private Sex(String folk, int age){
            this.age=age;
            this.folk=folk;
        }
    }

    private interface RefinePerson{
         Person fixGender(Person p);
    }

    private static class ReformGender{
        private static Person person;
        private static Person fixGender(Person p){
            switch (p.sex.folk.toLowerCase()){
                case "çocuk":
                    if(p.getAge()>=18)
                        p.setSex(Sex.MALE);
                    break;
                case "kız":
                    if(p.getAge()>=18)
                        p.setSex(Sex.WOMAN);
                    break;
                case "adam":
                    if(p.getAge()<18)
                        p.setSex(Sex.BOY);
                    break;
                case "kadın":
                    if(p.getAge()<18)
                        p.setSex(Sex.GIRL);
                    break;
                default:
                    break;
            }
            System.out.println("Reformed person: " + p);
            return p;
        }
    }

    private class StraightenGender{
        private Person person;
        private Person fixGender(Person p){
            switch (p.sex.folk.toLowerCase()){
                case "kız":
                    if(p.getAge()>=18)
                        p.setSex(Sex.WOMAN);
                    break;
                case "çocuk":
                    if(p.getAge()>=18)
                        p.setSex(Sex.MALE);
                    break;
                case "kadın":
                    if(p.getAge()<18)
                        p.setSex(Sex.GIRL);
                    break;
                case "adam":
                    if(p.getAge()<18)
                        p.setSex(Sex.BOY);
                    break;
                default:
                    break;
            }
            System.out.println("Straightened person: " + p);
            return p;
        }
    }

    public Person(String name, int age, Sex sex){
        this.name=name;
        this.age=age;
        this.sex=sex;

        ReformGender.fixGender(this);

        StraightenGender straightenGender = new StraightenGender();
        straightenGender.fixGender(this);

        RefinePerson refinePersonImpl = new RefinePerson() {
            @Override
            public Person fixGender(Person p) {
                switch (p.sex.folk.toLowerCase()){
                    case "kız":
                        if(p.getAge()>=18)
                            p.setSex(Sex.WOMAN);
                        break;
                    case "çocuk":
                        if(p.getAge()>=18)
                            p.setSex(Sex.MALE);
                        break;
                    case "kadın":
                        if(p.getAge()<18)
                            p.setSex(Sex.GIRL);
                        break;
                    case "adam":
                        if(p.getAge()<18)
                            p.setSex(Sex.BOY);
                        break;
                    default:
                        break;
                }
                System.out.println("Refined person: " + p);
                return p;
            }
        };
        refinePersonImpl.fixGender(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getGender() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    static List<Person> createRoster(){
        Person person1 = new Person("mike",11, Sex.MALE);
        Person person2 = new Person("john",21, Sex.MALE);
        Person person3 = new Person("jason",31, Sex.MALE);
        Person person4 = new Person("roger",43, Sex.MALE);
        Person person5 = new Person("jane",8, Sex.WOMAN);
        Person person6 = new Person("mary",18, Sex.WOMAN);
        Person person7 = new Person("jill",14, Sex.WOMAN);
        Person person8 = new Person("kylie",48, Sex.WOMAN);
        Person[] personArray = {person1,person2,person3,person4,person5,person6,person7,person8};
        List<Person>listPerson=new ArrayList<>();
        for(Person person : personArray){
            listPerson.add(person);
        }
        return listPerson;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
    public String printPerson(){
         return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
