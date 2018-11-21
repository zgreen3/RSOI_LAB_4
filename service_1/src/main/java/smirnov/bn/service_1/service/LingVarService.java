package smirnov.bn.service_1.service;

import javax.annotation.Nullable;
import java.util.List;

import smirnov.bn.service_1.model.LingVarInfo;

public interface LingVarService {

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

}
