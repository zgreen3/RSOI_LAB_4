package smirnov.bn.service_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import smirnov.bn.service_2.entity.BusinessProcDesc;

public interface BusinessProcDescRepository
        extends JpaRepository<BusinessProcDesc, Integer> {
}
