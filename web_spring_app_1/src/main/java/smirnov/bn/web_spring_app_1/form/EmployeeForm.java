package smirnov.bn.web_spring_app_1.form;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
//https://spring.io/guides/gs/validating-form-input/
//https://stackoverflow.com/questions/34454719/spring-boot-thymeleaf-custom-error-messages
//http://programmertech.com/program/jee/spring-mvc-form-validation-with-thymeleaf
//
//https://www.baeldung.com/thymeleaf-in-spring-mvc
//https://www.baeldung.com/spring-boot-crud-thymeleaf
//https://www.baeldung.com/javax-validation

//https://o7planning.org/en/11545/spring-boot-and-thymeleaf-tutorial
public class EmployeeForm {
    private Integer employeeId;

    @NotBlank(message = "Employee's name is mandatory")
    private String employeeName;

    private String employeeEmail;

    private String employeeLogin;

    private UUID employeeUuid;

    public EmployeeForm() {
    }

    public EmployeeForm(Integer employeeId, String employeeName, String employeeEmail, String employeeLogin, UUID employeeUuid) {
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
