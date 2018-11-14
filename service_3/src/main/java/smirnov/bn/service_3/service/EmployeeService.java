package smirnov.bn.service_3.service;


import smirnov.bn.service_3.entity.Employee;
import smirnov.bn.service_3.model.EmployeeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {


    /*
    @Nullable
    void createEmployee(@Nonnull String employeeName, @Nonnull String employeeEmail, @Nonnull String employeeLogin);
    //*/
    UUID createEmployee(@Nonnull EmployeeInfo employeeInfo);

    @Nullable //@Nonnull
    List<EmployeeInfo> findAllEmployees();

    @Nullable
    List<EmployeeInfo> findAllEmployeesPaginated(int page, int sizeLimit);

    /*
    @Nullable
    EmployeeInfo findEmployeeById(@Nonnull Integer lingVarId);
    //*/

    @Nullable
    EmployeeInfo findEmployeeByUuid(@Nonnull UUID uuid);

    //void updateEmployee(@Nonnull String employeeName, @Nonnull String employeeEmail, @Nonnull String employeeLogin, @Nonnull UUID employeeUuid);

    void updateEmployee(@Nonnull EmployeeInfo employeeInfo);

    //void deleteEmployeeById(@Nonnull Integer employeeId);

    void deleteEmployeeByUuid(@Nonnull UUID employeeUuid);
}
