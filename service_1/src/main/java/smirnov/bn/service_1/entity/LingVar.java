package smirnov.bn.service_1.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "linguistic_variables")
public class LingVar {
    @Id
    @Column(name = "ling_var_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lingVarId;

    @Column(name = "ling_var_name")
    private String lingVarName;

    @Column(name = "ling_var_trm_low_val")
    private Integer lingVarTermLowVal;

    @Column(name = "ling_var_trm_med_val")
    private Integer lingVarTermMedVal;

    @Column(name = "ling_var_trm_high_val")
    private Integer lingVarTermHighVal;

    //N.B.: используем здесь тип String, а не UUID, для упрощения работы с ним,
    //т.к. это справочное ссылочное поле, которому не нужны качества типа данных UUID:
    @Column(name = "employee_uuid")
    private String employeeUuid;

    public Integer getLingVarId() {
        return lingVarId;
    }

    public String getLingVarName() {
        return lingVarName;
    }

    public Integer getLingVarTermLowVal() {
        return lingVarTermLowVal;
    }

    public Integer getLingVarTermMedVal() {
        return lingVarTermMedVal;
    }

    public Integer getLingVarTermHighVal() {
        return lingVarTermHighVal;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setLingVarId(Integer lingVarId) {
        this.lingVarId = lingVarId;
    }

    public void setLingVarName(String lingVarName) {
        this.lingVarName = lingVarName;
    }

    public void setLingVarTermLowVal(Integer lingVarTermLowVal) {
        this.lingVarTermLowVal = lingVarTermLowVal;
    }

    public void setLingVarTermMedVal(Integer lingVarTermMedVal) {
        this.lingVarTermMedVal = lingVarTermMedVal;
    }

    public void setLingVarTermHighVal(Integer lingVarTermHighVal) {
        this.lingVarTermHighVal = lingVarTermHighVal;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LingVar lingVar = (LingVar) o;
        return Objects.equals(lingVarId, lingVar.lingVarId) &&
                Objects.equals(lingVarName, lingVar.lingVarName) &&
                Objects.equals(lingVarTermLowVal, lingVar.lingVarTermLowVal) &&
                Objects.equals(lingVarTermMedVal, lingVar.lingVarTermMedVal) &&
                Objects.equals(lingVarTermHighVal, lingVar.lingVarTermHighVal) &&
                Objects.equals(employeeUuid, lingVar.employeeUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lingVarId, lingVarName, lingVarTermLowVal, lingVarTermMedVal, lingVarTermHighVal, employeeUuid);
    }

    @Override
    public String toString() {
        return "LingVar{" +
                "lingVarId=" + lingVarId +
                ", lingVarName='" + lingVarName + '\'' +
                ", lingVarTermLowVal=" + lingVarTermLowVal +
                ", lingVarTermMedVal=" + lingVarTermMedVal +
                ", lingVarTermHighVal=" + lingVarTermHighVal +
                ", employeeUuid=" + employeeUuid +
                '}';
    }
}
