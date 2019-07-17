package net.byte2data.linxa.pojo;

import java.io.Serializable;

public class Employe implements Serializable {

    private static final long serialVersionUID = 7922505822814669670L;

    private Object employeeId;
    private String employeeName;
    private OtherAddress employeeAddress;
    private Double employeeSalary;
    private Integer anyInteger;


    public Employe(Object employeeId, String employeeName, OtherAddress employeeAddress, Double employeeSalary){
        this.employeeId=employeeId;
        this.employeeName=employeeName;
        this.employeeAddress=employeeAddress;
        this.employeeSalary=employeeSalary;
        this.anyInteger = (int) (Math.random()*1000);
    }


    public Object getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Object employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public OtherAddress getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(OtherAddress employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public Double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(Double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public boolean equals(Object kindOfEmployee) {
        if(null!=kindOfEmployee){
            if(kindOfEmployee instanceof Employe){
                //return this.getEmployeeSalary().equals(((Employe)(kindOfEmployee)).getEmployeeSalary());

            }
        }
        return false;
    }


    public Integer getAnyInteger() {
        return anyInteger;
    }

    public void setAnyInteger(Integer anyInteger) {
        this.anyInteger = anyInteger;
    }



    @Override
    public String toString() {
        return "Employe{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeAddress=" + employeeAddress +
                ", employeeSalary=" + employeeSalary +
                ", anyInteger=" + anyInteger +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalizing... " + this);
        super.finalize();
    }
}
