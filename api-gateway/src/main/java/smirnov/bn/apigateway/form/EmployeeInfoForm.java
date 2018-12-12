package smirnov.bn.apigateway.form;

import java.util.UUID;


//https://o7planning.org/en/11545/spring-boot-and-thymeleaf-tutorial
public class EmployeeInfoForm {
    private Integer employeeId;

    private String employeeName;

    private String employeeEmail;

    private String employeeLogin;

    private UUID employeeUuid;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }

    public UUID getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(UUID employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
