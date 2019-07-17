package net.byte2data.consept.orm.employee;

import net.byte2data.consept.orm.DBLayer;
import net.byte2data.consept.orm.employee.pojo.Employee;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmployeeFeeder implements Callable<Employee> {

    private static final int NTHREAD=Runtime.getRuntime().availableProcessors();
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(NTHREAD);

    private EmployeeMapResource resource;
    private Employee employee;
    public EmployeeFeeder(EmployeeMapResource resource, Employee employee){
        this.resource=resource;
        this.employee=employee;
    }

    @Override
    public Employee call() throws Exception {
        //System.out.println("Feeding to resource -> " + employee.getSalary());
        resource.enQueue(employee);
        EmployeeFetcher employeeFetcher = new EmployeeFetcher(resource, employee);
        scheduledExecutorService.schedule(employeeFetcher, ((employee.getSalary()%3==0) ? 10000 : 0), TimeUnit.MILLISECONDS);
        /*
        System.out.println("FEEDER SERVICE IS SHUTTING DOWN...");
        scheduledExecutorService.shutdown();
        while(!scheduledExecutorService.isTerminated()){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("FEEDER SERVICE CLOSED");
        */
        return null;
    }
}
