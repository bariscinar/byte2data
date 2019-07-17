package net.byte2data.consept.orm.employee.simpleloadtest;

import net.byte2data.consept.orm.DBLayer;
import net.byte2data.consept.orm.employee.pojo.Employee;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private static class LoadEmployees implements Runnable{
        private EmployeeManager employeeManagement;
        public LoadEmployees(EmployeeManager employeeManagement){
            this.employeeManagement=employeeManagement;
        }

        @Override
        public void run() {
            int id = employeeManagement.create(new Employee("name", "surname", (int)(10*(Math.random()))));
            //System.out.println("id: " +id);
        }
    }


    public static void main(String... args) throws InterruptedException{
        ExecutorService service = Executors.newFixedThreadPool(15);

        EmployeeManager employeeManagement = new EmployeeManager();
        System.out.println("Creating employees...");

        for(int index=0; index<10000; index++){
            service.submit(new LoadEmployees(employeeManagement));
        }

        System.out.println("SHUTDOWN STARTING....");
        service.shutdown();

        while (!service.isTerminated()){
            Thread.sleep(2000);
        }

        System.out.println("SHUTDOWN COMPLETED....");
        /*
        System.out.println("Listing all employees...");
        List<Employe> employees = employeeManagement.list();

        for(Employe employee : employees){
            System.out.println(employee.toString());
        }
        Thread.sleep(2000);
        */

        DBLayer.getInstance().destroyFacyory();
        Thread.sleep(1000);

        //System.exit(1);

    }

}
