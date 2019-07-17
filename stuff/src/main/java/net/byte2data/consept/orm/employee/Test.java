package net.byte2data.consept.orm.employee;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String... args){

        EmployeeMapResource resource = new EmployeeMapResource();
        EmployeeGenerator employeeGenerator = new EmployeeGenerator(resource);
        service.submit(employeeGenerator);

        System.out.println("MAIN SERVICE IS SHUTTING DOWN...");
        service.shutdown();
        while(!service.isTerminated()){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("MAIN SERVICE CLOSED");

    }

}
