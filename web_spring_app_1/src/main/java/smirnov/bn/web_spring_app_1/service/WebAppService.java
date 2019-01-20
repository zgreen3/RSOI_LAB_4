package smirnov.bn.web_spring_app_1.service;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

import smirnov.bn.web_spring_app_1.model.EmployeeInfo;

public interface WebAppService {
    /*
    @Nullable
    Integer createLingVar(LingVarInfo lingVarInfo);

    @Nullable
    List<LingVarInfo> findAllLingVars();

    @Nullable
    List<LingVarInfo> findAllLingVarPaginated(int page, int sizeLimit);

    @Nullable
    LingVarInfo findLingVarById(Integer lingVarId);

    @Nullable
    List<LingVarInfo> findLingVarsByEmployeeUuid(String employeeUuid);

    void updateLingVar(LingVarInfo lingVarInfo);

    void deleteLingVarById(Integer lingVarId);
    //*/

    /*
    @Nullable
    Integer createBusinessProcDesc(BusinessProcDescInfo businessProcDescInfo);

    @Nullable
    List<BusinessProcDescInfo> findAllBusinessProcDescs();

    @Nullable
    List<BusinessProcDescInfo> findAllBusinessProcDescsPaginated(int page, int sizeLimit);

    @Nullable
    BusinessProcDescInfo findBusinessProcDescById(Integer bizProcId);

    @Nullable
    List<BusinessProcDescInfo> findBusinessProcDescByEmployeeUuid(String employeeUuid);

    void updateBusinessProcDesc(BusinessProcDescInfo businessProcDescInfo);

    void deleteBusinessProcDescById(Integer bizProcId);
    //*/

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
