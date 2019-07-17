package net.byte2data.consept.orm.employee.simpleloadtest;

import net.byte2data.consept.orm.DBLayer;
import net.byte2data.consept.orm.employee.EmployeeMapResource;
import net.byte2data.consept.orm.employee.pojo.Employee;
import net.byte2data.consept.orm.utilities.NullCheck;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeManager {// implements Callable<Employe>{

    private DBLayer dbLayer;
    private EmployeeMapResource resource;
    private Employee singleEmployee;

    public EmployeeManager(){
        this.dbLayer = DBLayer.getInstance();
    }


    public Integer create(Employee employee){
        Session session = null;
        Transaction transaction = null;
        Integer recentEmployeeId = null;
        NullCheck<Employee> employeeNullCheck = new NullCheck<>(employee);

        if(!employeeNullCheck.isNull()){
            //System.out.println("Trying to create employee: " + employee.toString());
            try{
                session = dbLayer.getSession();
                transaction = session.beginTransaction();
                recentEmployeeId = (Integer) session.save(employee);
                //session.persist(employee);
                recentEmployeeId=employee.getId();
                transaction.commit();
                //System.out.println("Recently added employee id: " + recentEmployeeId);
            }catch (Exception ex){
                System.out.println("Exception occurred: " + ex);

                if(null!=transaction){
                    //System.out.println("Transaction is being rolled back...");
                    transaction.rollback();
                }else{
                    //System.out.println("Transaction is already null!");
                }

            }finally {
                if(null!=session){
                    //System.out.println("Session is being closed...");
                    dbLayer.putSession(session);
                }else{
                    //System.out.println("session is already null!");
                }

            }
        }else{
            System.out.println("Employe is null!");
        }
        return recentEmployeeId;
    }

    public List<Employee> list(){

        Session session = null;
        //Transaction transaction = null;
        List<Employee> employees = null;

            System.out.println("Trying to list employees... ");
            try{
                session = dbLayer.getSession();
                //transaction = session.beginTransaction();
                employees = (List<Employee>) session.createQuery("FROM Employee").list();
                //transaction.commit();
            }catch (Exception ex){
                System.out.println("Exception occurred: " + ex);
                /*
                if(null!=transaction){
                    System.out.println("Transaction is being rolled back...");
                    transaction.rollback();
                }else{
                    System.out.println("Transaction is already null!");
                }
                */

            }finally {
                if(null!=session){
                    System.out.println("Session is being closed...");
                    dbLayer.putSession(session);
                }else{
                    System.out.println("session is already null!");
                }

            }

        return employees;

    }

}
