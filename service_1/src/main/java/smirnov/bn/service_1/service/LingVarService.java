package smirnov.bn.service_1.service;

import smirnov.bn.service_1.model.LingVarInfo;

import javax.annotation.Nullable;
import java.util.List;

public interface LingVarService {

    @Nullable
    Integer createLingVar(LingVarInfo lingVarInfo);

    @Nullable
    List<LingVarInfo> findAllLingVars();

    @Nullable
    List<LingVarInfo> findAllLingVarPaginated(int page, int sizeLimit);

    @Nullable
    LingVarInfo findLingVarById(Integer lingVarId);

    void updateLingVar(LingVarInfo lingVarInfo);

    void deleteLingVarById(Integer lingVarId);

}
