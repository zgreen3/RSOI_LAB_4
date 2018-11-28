package smirnov.bn.service_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import smirnov.bn.service_2.entity.BusinessProcDesc;

import java.util.List;

public interface BusinessProcDescRepository
        extends JpaRepository<BusinessProcDesc, Integer> {
    @Query(value = "SELECT bpd.* FROM business_proc_desc bpd WHERE bpd.biz_proc_id = :bizProcId",
            nativeQuery = true)
    BusinessProcDesc findBusinessProcDescById(@Param("bizProcId") Integer bizProcId);

    @Query(value = "SELECT bpd.* FROM business_proc_desc bpd WHERE bpd.employee_uuid = :employeeUuid",
            nativeQuery = true)
    List<BusinessProcDesc> findBusinessProcDescByEmployeeUuid(@Param("employeeUuid") String employeeUuid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE business_proc_desc SET biz_proc_name = :bizProcName, biz_proc_desc_str = :bizProcDescStr, employee_uuid = :employeeUuid WHERE biz_proc_id = :bizProcId",
            nativeQuery = true)
    void updateBusinessProcDesc(@Param("bizProcId") Integer bizProcId, @Param("bizProcName") String bizProcName,
                       @Param("bizProcDescStr") String bizProcDescStr, @Param("employeeUuid") String employeeUuid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM business_proc_desc bpd WHERE bpd.biz_proc_id = :bizProcId",
            nativeQuery = true)
    void deleteById(@Param("bizProcId") Integer bizProcId);
}
