package smirnov.bn.apigateway.info_model_patterns;

import smirnov.bn.apigateway.info_model_patterns.LingVarInfo;
import smirnov.bn.apigateway.info_model_patterns.EmployeeInfo;

public class LingVarWithEmployeeInfo {
    private Integer lingVarId;
    private String lingVarName;
    private Integer lingVarTermLowVal;
    private Integer lingVarTermMedVal;
    private Integer lingVarTermHighVal;
    private Integer employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeLogin;
    private String employeeUuid;

    //"default" constructor:
    public LingVarWithEmployeeInfo() {}

    public LingVarWithEmployeeInfo(LingVarInfo lingVarInfo, EmployeeInfo employeeInfo) {
        this.lingVarId = lingVarInfo.getLingVarId();
        this.lingVarName = lingVarInfo.getLingVarName();
        this.lingVarTermLowVal = lingVarInfo.getLingVarTermLowVal();
        this.lingVarTermMedVal = lingVarInfo.getLingVarTermMedVal();
        this.lingVarTermHighVal = lingVarInfo.getLingVarTermHighVal();
        this.employeeId = employeeInfo.getEmployeeId();
        this.employeeName = employeeInfo.getEmployeeName();
        this.employeeEmail = employeeInfo.getEmployeeEmail();
        this.employeeLogin = employeeInfo.getEmployeeLogin();
        this.employeeUuid = employeeInfo.getEmployeeUuid().toString();
    }

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

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
