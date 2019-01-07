package smirnov.bn.web_spring_app_1.model;

import java.util.UUID;

public class EmployeeInfo
{
    private Integer employeeId;

    private String employeeName;

    private String employeeEmail;

    private String employeeLogin;

    private UUID employeeUuid;

    public EmployeeInfo() {
    }

    public EmployeeInfo(String employeeName, String employeeEmail, String employeeLogin) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeLogin = employeeLogin;
    }

    public EmployeeInfo(Integer employeeId, String employeeName, String employeeEmail, String employeeLogin, UUID employeeUuid) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeLogin = employeeLogin;
        this.employeeUuid = employeeUuid;
    }

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
