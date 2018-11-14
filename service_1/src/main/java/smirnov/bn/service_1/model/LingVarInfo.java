package smirnov.bn.service_1.model;

public class LingVarInfo {
    private Integer lingVarId;
    private String lingVarName;
    private Integer lingVarTermLowVal;
    private Integer lingVarTermMedVal;
    private Integer lingVarTermHighVal;
    private String employeeUuid;

    public Integer getLingVarId() {
        return lingVarId;
    }

    public void setLingVarId(Integer lingVarId) {
        this.lingVarId = lingVarId;
    }

    public String getLingVarName() {
        return lingVarName;
    }

    public void setLingVarName(String lingVarName) {
        this.lingVarName = lingVarName;
    }

    public Integer getLingVarTermLowVal() {
        return lingVarTermLowVal;
    }

    public void setLingVarTermLowVal(Integer lingVarTermLowVal) {
        this.lingVarTermLowVal = lingVarTermLowVal;
    }

    public Integer getLingVarTermMedVal() {
        return lingVarTermMedVal;
    }

    public void setLingVarTermMedVal(Integer lingVarTermMedVal) {
        this.lingVarTermMedVal = lingVarTermMedVal;
    }

    public Integer getLingVarTermHighVal() {
        return lingVarTermHighVal;
    }

    public void setLingVarTermHighVal(Integer lingVarTermHighVal) {
        this.lingVarTermHighVal = lingVarTermHighVal;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
