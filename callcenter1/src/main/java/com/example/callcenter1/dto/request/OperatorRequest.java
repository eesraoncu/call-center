package com.example.callcenter1.dto.request;

public class OperatorRequest {
    private String operatorName;
    private String operatorSurname;
    private String operatorPhoneNumber;
    private String operatorEmail;
    private String operatorPassword;
    private Integer roleId;

    public OperatorRequest() {}

    public OperatorRequest(String operatorName, String operatorSurname, String operatorPhoneNumber, String operatorEmail, String operatorPassword, Integer roleId) {
        this.operatorName = operatorName;
        this.operatorSurname = operatorSurname;
        this.operatorPhoneNumber = operatorPhoneNumber;
        this.operatorEmail = operatorEmail;
        this.operatorPassword = operatorPassword;
        this.roleId = roleId;
    }

    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }

    public String getOperatorSurname() { return operatorSurname; }
    public void setOperatorSurname(String operatorSurname) { this.operatorSurname = operatorSurname; }

    public String getOperatorPhoneNumber() { return operatorPhoneNumber; }
    public void setOperatorPhoneNumber(String operatorPhoneNumber) { this.operatorPhoneNumber = operatorPhoneNumber; }

    public String getOperatorEmail() { return operatorEmail; }
    public void setOperatorEmail(String operatorEmail) { this.operatorEmail = operatorEmail; }

    public String getOperatorPassword() { return operatorPassword; }
    public void setOperatorPassword(String operatorPassword) { this.operatorPassword = operatorPassword; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
}
