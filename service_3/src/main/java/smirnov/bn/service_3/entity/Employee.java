package smirnov.bn.service_3.entity;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Configuration
class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Employee.class);
    }
}

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(name = "employee_name", length = 255)
    private String employeeName;

    @Column(name = "employee_email", length = 255)
    private String employeeEmail;

    @Column(name = "employee_login", length = 255)
    private String employeeLogin;

    @Column(name = "employee_uuid", unique = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(employeeName, employee.employeeName) &&
                Objects.equals(employeeEmail, employee.employeeEmail) &&
                Objects.equals(employeeLogin, employee.employeeLogin) &&
                Objects.equals(employeeUuid, employee.employeeUuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(employeeId, employeeName, employeeEmail, employeeLogin, employeeUuid);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeLogin='" + employeeLogin + '\'' +
                ", employeeUuid=" + employeeUuid +
                '}';
    }
}