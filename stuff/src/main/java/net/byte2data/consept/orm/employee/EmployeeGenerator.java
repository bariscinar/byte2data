package net.byte2data.consept.orm.employee;

import net.byte2data.consept.orm.employee.pojo.Employee;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeeGenerator implements Callable<Employee> {

    private static final int NTHREAD = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(NTHREAD);

    private EmployeeMapResource resource;

    public EmployeeGenerator(EmployeeMapResource resource){
        this.resource=resource;
    }

    @Override
    public Employee call() throws Exception {
        Employee employee;
        for(int index=0; index<100; index++){
            employee = new Employee("name-"+index,"surname-"+index,index);
            //System.out.println("Generating -> " + employee.getSalary());
            executorService.submit(new EmployeeFeeder(resource,employee));
        }
        System.out.println("GENERATOR SERVICE IS SHUTTING DOWN...");
        executorService.shutdown();
        while(!executorService.isTerminated()){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("GENERATOR SERVICE CLOSED");
        return null;
    }
}
