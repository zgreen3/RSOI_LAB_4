package smirnov.bn.service_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Collectors;

import smirnov.bn.service_1.entity.LingVar;
import smirnov.bn.service_1.model.LingVarInfo;
import smirnov.bn.service_1.repository.LingVarRepository;

@Service
public class LingVarServiceImpl implements LingVarService {

    @Autowired
    private LingVarRepository lingVarRepository;

    @Nonnull
    private LingVarInfo buildlingVarInfo(LingVar lingVar) {
        LingVarInfo info = new LingVarInfo();
        info.setLingVarName(lingVar.getLingVarName());
        info.setLingVarTermLowVal(lingVar.getLingVarTermLowVal());
        info.setLingVarTermMedVal(lingVar.getLingVarTermMedVal());
        info.setLingVarTermHighVal(lingVar.getLingVarTermHighVal());
        info.setEmployeeUuid(lingVar.getEmployeeUuid());
        return info;
    }

    @Nullable
    @Override
    @Transactional
    public Integer createLingVar(LingVarInfo lingVarInfo) {
        LingVar lingVar = new LingVar();
        lingVar.setLingVarName(lingVarInfo.getLingVarName());
        lingVar.setLingVarTermLowVal(lingVarInfo.getLingVarTermLowVal());
        lingVar.setLingVarTermMedVal(lingVarInfo.getLingVarTermMedVal());
        lingVar.setLingVarTermHighVal(lingVarInfo.getLingVarTermHighVal());
        lingVar.setEmployeeUuid(lingVarInfo.getEmployeeUuid());
        lingVarRepository.saveAndFlush(lingVar);
        return lingVar.getLingVarId();
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<LingVarInfo> findAllLingVars() {
        return lingVarRepository.findAll()
                .stream()
                .map(this::buildlingVarInfo)
                .collect(Collectors.toList());
    }

    //http://www.appsdeveloperblog.com/rest-pagination-tutorial-with-spring-mvc/
    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<LingVarInfo> findAllLingVarPaginated(int page, int sizeLimit) {
        List<LingVarInfo> lingVarInfoList = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, sizeLimit);
        Page<LingVar> lingVarsPage = lingVarRepository.findAll(pageableRequest);
        List<LingVar> lingVars = lingVarsPage.getContent();
        for (LingVar lingVar : lingVars) {
            lingVarInfoList.add(this.buildlingVarInfo(lingVar));
        }
        return lingVarInfoList;
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public LingVarInfo findLingVarById(@Nonnull Integer lingVarId) {
        return this.buildlingVarInfo(lingVarRepository.findLingVarById(lingVarId));
    }

    @Override
    @Transactional
    public void updateLingVar(@Nonnull LingVarInfo lingVarInfo) {
        lingVarRepository.updateLingVar(lingVarInfo.getLingVarId(), lingVarInfo.getLingVarName(), lingVarInfo.getLingVarTermLowVal(),
                lingVarInfo.getLingVarTermMedVal(), lingVarInfo.getLingVarTermHighVal(), lingVarInfo.getEmployeeUuid());
    }

    @Override
    @Transactional
    public void deleteLingVarById(@Nonnull Integer lingVarId) {
        lingVarRepository.deleteById(lingVarId);
    }

}
