package smirnov.bn.apigateway.info_model_patterns;

public class BizDescWithEmployeeInfo {
    private Integer bizProcId;
    private String bizProcName;
    private String bizProcDescStr;
    private Integer employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeLogin;
    private String employeeUuid;

    //"default" constructor:
    public BizDescWithEmployeeInfo() {
    }

    public BizDescWithEmployeeInfo(BusinessProcDescInfo businessProcDescInfo, EmployeeInfo employeeInfo) {
        this.bizProcId = businessProcDescInfo.getBizProcId();
        this.bizProcName = businessProcDescInfo.getBizProcName();
        this.bizProcDescStr = businessProcDescInfo.getBizProcDescStr();
        this.employeeId = employeeInfo.getEmployeeId();
        this.employeeName = employeeInfo.getEmployeeName();
        this.employeeEmail = employeeInfo.getEmployeeEmail();
        this.employeeLogin = employeeInfo.getEmployeeLogin();
        this.employeeUuid = employeeInfo.getEmployeeUuid().toString();
    }

    public Integer getBizProcId() {
        return bizProcId;
    }

    public void setBizProcId(Integer bizProcId) {
        this.bizProcId = bizProcId;
    }

    public String getBizProcName() {
        return bizProcName;
    }

    public void setBizProcName(String bizProcName) {
        this.bizProcName = bizProcName;
    }

    public String getBizProcDescStr() {
        return bizProcDescStr;
    }

    public void setBizProcDescStr(String bizProcDescStr) {
        this.bizProcDescStr = bizProcDescStr;
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