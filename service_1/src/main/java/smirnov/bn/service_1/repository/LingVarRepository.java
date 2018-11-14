package smirnov.bn.service_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import smirnov.bn.service_1.entity.LingVar;

public interface LingVarRepository
        extends JpaRepository<LingVar, Integer> {
    @Query(value = "SELECT lv.* FROM linguistic_variables lv WHERE lv.ling_var_id = :lingVarId",
            nativeQuery = true)
    LingVar findLingVarById(@Param("lingVarId") Integer lingVarId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE linguistic_variables lv SET lv.ling_var_name = :lingVarName, lv.ling_var_trm_low_val = :lingVarTermLowVal, lv.ling_var_trm_med_val = :lingVarTermMedVal, lv.ling_var_trm_high_val = :lingVarTermHighVal, lv.employee_uuid = :employeeUuid WHERE lv.ling_var_id = :lingVarId",
            nativeQuery = true)
    void updateLingVar(@Param("lingVarId") Integer lingVarId, @Param("lingVarName") String lingVarName,
                       @Param("lingVarTermLowVal") Integer lingVarTermLowVal, @Param("lingVarTermMedVal") Integer lingVarTermMedVal,
                       @Param("lingVarTermHighVal") Integer lingVarTermHighVal, @Param("employeeUuid") String employeeUuid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM linguistic_variables lv WHERE lv.ling_var_id = :lingVarId",
            nativeQuery = true)
    void deleteById(@Param("lingVarId") Integer lingVarId);
}