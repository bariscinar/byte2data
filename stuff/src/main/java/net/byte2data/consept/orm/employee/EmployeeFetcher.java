package net.byte2data.consept.orm.employee;

import net.byte2data.consept.orm.DBLayer;
import net.byte2data.consept.orm.employee.pojo.Employee;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmployeeFetcher implements Callable<Employee> {
    private static DBLayer dbLayer;
    private EmployeeMapResource resource;
    private Employee employee;

    static {
         dbLayer = DBLayer.getInstance();
    }

    public EmployeeFetcher(EmployeeMapResource resource, Employee employee){
        this.resource=resource;
        this.employee=employee;
    }

    @Override
    public Employee call() throws Exception {
        Employee emp = resource.deQueue(employee);
        System.out.println("Fetching from resource -> " + emp.getSalary());
        return null;
    }
}
