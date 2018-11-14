package smirnov.bn.service_2.service;

import org.springframework.lang.Nullable;
import javax.annotation.Nonnull;
import java.util.List;

import smirnov.bn.service_2.model.BusinessProcDescInfo;

public interface BusinessProcDescService {

    @Nullable
    BusinessProcDescInfo createBusinessProcDesc(@Nonnull String businessProcDescName, @Nonnull String businessProcDescStr);

    @Nullable //@Nonnull
    List<BusinessProcDescInfo> findAllBusinessProcDesc();

    @Nullable
    List<BusinessProcDescInfo> findAllBusinessProcDescPaginated(int page, int sizeLimit);

    @Nullable
    BusinessProcDescInfo findBusinessProcDescById(@Nonnull Integer businessProcDescId);
}
