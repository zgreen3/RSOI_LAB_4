package smirnov.bn.service_2.service;

import javax.annotation.Nullable;
import java.util.List;

import smirnov.bn.service_2.model.BusinessProcDescInfo;

public interface BusinessProcDescService {

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
}
