package smirnov.bn.service_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private BusinessProcDescInfo buildlingBusinessProcDescInfo(BusinessProcDesc businessProcDesc) {
        BusinessProcDescInfo info = new BusinessProcDescInfo();
        info.setBizProcId(businessProcDesc.getBizProcId());
        info.setBizProcName(businessProcDesc.getBizProcName());
        info.setBizProcDescStr(businessProcDesc.getBizProcDescStr());
        info.setEmployeeUuid(businessProcDesc.getEmployeeUuid());
        return info;
    }

    @Nullable
    @Override
    @Transactional
    public Integer createBusinessProcDesc(BusinessProcDescInfo businessProcDescInfo) {
        BusinessProcDesc businessProcDesc = new BusinessProcDesc();
        //businessProcDesc.setBizProcId(businessProcDescInfo.getBizProcId());
        businessProcDesc.setBizProcName(businessProcDescInfo.getBizProcName());
        businessProcDesc.setBizProcDescStr(businessProcDescInfo.getBizProcDescStr());
        businessProcDesc.setEmployeeUuid(businessProcDescInfo.getEmployeeUuid());
        businessProcDescRepository.saveAndFlush(businessProcDesc);
        return businessProcDesc.getBizProcId();
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<BusinessProcDescInfo> findAllBusinessProcDescs() {
        return businessProcDescRepository.findAll()
                .stream()
                .map(this::buildlingBusinessProcDescInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<BusinessProcDescInfo> findAllBusinessProcDescsPaginated(int page, int sizeLimit) {
        List<BusinessProcDescInfo> businessProcDescInfo = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, sizeLimit);
        Page<BusinessProcDesc> businessProcDescsPage = businessProcDescRepository.findAll(pageableRequest);
        List<BusinessProcDesc> businessProcDescs = businessProcDescsPage.getContent();
        for (BusinessProcDesc businessProcDesc : businessProcDescs) {
            businessProcDescInfo.add(this.buildlingBusinessProcDescInfo(businessProcDesc));
        }
        return businessProcDescInfo;
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<BusinessProcDescInfo> findBusinessProcDescByEmployeeUuid(String employeeUuid) {
        return businessProcDescRepository.findBusinessProcDescByEmployeeUuid(employeeUuid)
                .stream()
                .map(this::buildlingBusinessProcDescInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public BusinessProcDescInfo findBusinessProcDescById(Integer businessProcDescId) {
        return this.buildlingBusinessProcDescInfo(businessProcDescRepository.findBusinessProcDescById(businessProcDescId));
    }

    @Override
    @Transactional
    public void updateBusinessProcDesc(BusinessProcDescInfo businessProcDescInfo) {
        businessProcDescRepository.updateBusinessProcDesc(businessProcDescInfo.getBizProcId(), businessProcDescInfo.getBizProcName(),
                businessProcDescInfo.getBizProcDescStr(), businessProcDescInfo.getEmployeeUuid());
    }

    @Override
    @Transactional
    public void deleteBusinessProcDescById(Integer bizProcId) {
        businessProcDescRepository.deleteById(bizProcId);
    }
}
