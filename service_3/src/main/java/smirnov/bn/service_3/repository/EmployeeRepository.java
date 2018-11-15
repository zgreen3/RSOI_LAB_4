package smirnov.bn.service_3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import smirnov.bn.service_3.entity.Employee;

import java.util.UUID;

public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT e.* FROM employees e WHERE e.employee_uuid = :employeeUuid",
            nativeQuery = true)
    Employee findByUuid(@Param("employeeUuid") UUID employeeUuid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE employees SET employee_name = :employeeName, employee_email = :employeeEmail, employee_login = :employeeLogin WHERE employee_uuid = :employeeUuid",
            nativeQuery = true)
    void updateEmployee(@Param("employeeName") String employeeName, @Param("employeeEmail") String employeeEmail,
                        @Param("employeeLogin") String employeeLogin, @Param("employeeUuid") UUID employeeUuid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM employees e WHERE e.employee_uuid = :employeeUuid",
           nativeQuery = true)
    void deleteByUuid(@Param("employeeUuid") UUID employeeUuid);
}
