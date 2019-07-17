package net.byte2data.consept.orm.employee;

import net.byte2data.consept.orm.employee.pojo.Employee;

import java.util.concurrent.ConcurrentHashMap;

public class EmployeeMapResource {

    private ConcurrentHashMap<Integer,Employee> employeeConcurrentHashMap;

    public EmployeeMapResource(){
        employeeConcurrentHashMap = new ConcurrentHashMap<>();
    }

    public void enQueue(Employee emp){
        employeeConcurrentHashMap.put(emp.getSalary(),emp);
    }

    public Employee deQueue(Employee emp){
        return employeeConcurrentHashMap.remove(emp.getSalary());
    }

}
