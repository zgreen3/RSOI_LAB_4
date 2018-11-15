package smirnov.bn.service_3.service;


import smirnov.bn.service_3.model.EmployeeInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {


    /*
    @Nullable
    void createEmployee(String employeeName, String employeeEmail, String employeeLogin);
    //*/
    UUID createEmployee(EmployeeInfo employeeInfo);

    @Nullable
    List<EmployeeInfo> findAllEmployees();

    @Nullable
    List<EmployeeInfo> findAllEmployeesPaginated(int page, int sizeLimit);

    /*
    @Nullable
    EmployeeInfo findEmployeeById(@Nonnull Integer lingVarId);
    //*/

    @Nullable
    EmployeeInfo findEmployeeByUuid(UUID uuid);

    //void updateEmployee(String employeeName, String employeeEmail, String employeeLogin, UUID employeeUuid);

    void updateEmployee(EmployeeInfo employeeInfo);

    //void deleteEmployeeById(Integer employeeId);

    void deleteEmployeeByUuid(UUID employeeUuid);
}
