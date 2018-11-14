package smirnov.bn.service_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import smirnov.bn.service_2.entity.BusinessProcDesc;
import smirnov.bn.service_2.model.BusinessProcDescInfo;
import smirnov.bn.service_2.repository.BusinessProcDescRepository;

@Service
public class BusinessProcDescServiceImpl implements BusinessProcDescService {

    @Autowired
    private BusinessProcDescRepository businessProcDescRepository;

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<BusinessProcDescInfo> findAllBusinessProcDesc() {
        return businessProcDescRepository.findAll()
                .stream()
                .map(this::buildlingBusinessProcDescInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<BusinessProcDescInfo> findAllBusinessProcDescPaginated(int page, int sizeLimit) {
        List<BusinessProcDescInfo> businessProcDescInfoList = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, sizeLimit);
        Page<BusinessProcDesc> businessProcDescsPage = businessProcDescRepository.findAll(pageableRequest);
        List<BusinessProcDesc> businessProcDescs = businessProcDescsPage.getContent();
        for (BusinessProcDesc businessProcDesc : businessProcDescs) {
            businessProcDescInfoList.add(this.buildlingBusinessProcDescInfo(businessProcDesc));
        }
        return businessProcDescInfoList;
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public BusinessProcDescInfo findBusinessProcDescById(@Nonnull Integer businessProcDescId) {
        return businessProcDescRepository.findById(businessProcDescId).map(this::buildlingBusinessProcDescInfo).orElse(null);
    }

    @Nonnull
    private BusinessProcDescInfo buildlingBusinessProcDescInfo(BusinessProcDesc businessProcDesc) {
        BusinessProcDescInfo info = new BusinessProcDescInfo();
        info.setBizProcName(businessProcDesc.getBizProcName());
        info.setBizProcDescStr(businessProcDesc.getBizProcDescStr());
        return info;
    }

    @Nullable
    @Override
    @Transactional
    public BusinessProcDescInfo createBusinessProcDesc(@Nonnull String businessProcDescName, @Nonnull String businessProcDescStr) {
        BusinessProcDesc businessProcDesc = new BusinessProcDesc();
        businessProcDesc.setBizProcName(businessProcDescName);
        businessProcDesc.setBizProcDescStr(businessProcDescStr);
        return this.buildlingBusinessProcDescInfo(businessProcDescRepository.saveAndFlush(businessProcDesc));
    }
}
