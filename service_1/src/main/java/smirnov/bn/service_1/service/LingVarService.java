package smirnov.bn.service_1.service;

import org.springframework.http.ResponseEntity;
import smirnov.bn.service_1.model.LingVarInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface LingVarService {

    @Nullable
    Integer createLingVar(LingVarInfo lingVarInfo);

    @Nullable //@Nonnull
    List<LingVarInfo> findAllLingVars();

    @Nullable
    List<LingVarInfo> findAllLingVarPaginated(int page, int sizeLimit);

    @Nullable
    LingVarInfo findLingVarById(@Nonnull Integer lingVarId);

    void updateLingVar(@Nonnull LingVarInfo lingVarInfo);

    void deleteLingVarById(@Nonnull Integer lingVarId);

}
