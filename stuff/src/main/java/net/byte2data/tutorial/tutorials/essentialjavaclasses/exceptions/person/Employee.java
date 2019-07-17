package net.byte2data.tutorial.tutorials.essentialjavaclasses.exceptions.person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barisci on 21.09.2017.
 */
public class Employee implements AutoCloseable {

    private List<Person> employeeList;

    public class ApplierImpl implements Applier{
        @Override
        public boolean apply(Person obj) {
            return obj.getAge() > 41;
        }
    }

    public Employee(int employeeCount) {
        employeeList = new ArrayList<>();
        Person p = null;
        for(int index=0; index<employeeCount; index++){
            p = new Person(20+index,"name"+String.valueOf(index), index%2==0? Person.Gender.FEMALE: Person.Gender.MALE);
            employeeList.add(p);
        }
    }

    public void listApplicableEmployee(Applier executionRule){
        for(Person p : employeeList){
            if (executionRule.apply(p))
                    System.out.println(p.toString());
        }
    }

    public void listApplicableEmployee(ApplierImpl executionRule){
        for(Person p : employeeList){
            if (executionRule.apply(p))
                System.out.println(p.toString());
        }
    }


    @Override
    public void close() throws Exception {
        System.out.println("Employe close method called!");
    }



}
