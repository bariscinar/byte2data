package net.byte2data.tutorial.tutorials.essentialjavaclasses.exceptions.person;

/**
 * Created by barisci on 21.09.2017.
 */
public class Tester {

    public static void main(String... args) throws  Exception{

        try(Employee employee = new Employee(200)){
            employee.listApplicableEmployee(new Applier() {
                public boolean apply(Person obj) {
                    return obj.getGender().equals(Person.Gender.FEMALE)&&obj.getAge()%3==0;
                }
            });
            Employee.ApplierImpl impl = employee.new ApplierImpl();
            employee.listApplicableEmployee(employee.new ApplierImpl());

        }
    }

}
