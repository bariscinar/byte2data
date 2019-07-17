package net.byte2data.consept.orm.employee.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Employee implements Serializable{
    /*
    TODO: We will have a serious problem in HIBERNATE upgrade
    Now we are using hibernate 4.2.2
    If it is planning to use 5.2.4 and above we will have issue with sequences!
    I have tried to add annotations below but it does not work!
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @GenericGenerator(name="employee_sequence", strategy = "native")
    And finally i have found the solution!
    https://developer.jboss.org/thread/269045?_sscc=t
    As you will see in the hbm.xml file of the related Entity, you should make the change below;
    <param name="sequence">employee_sequence</param>
    <param name="sequence_name">employee_sequence</param>
    The first one is the old definition (sequence), second one is the new definition (sequence_name)
    */
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    public Employee(){}

    public Employee(String fname, String lname, int salary) {
        this.firstName = fname;
        this.lastName = lname;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
