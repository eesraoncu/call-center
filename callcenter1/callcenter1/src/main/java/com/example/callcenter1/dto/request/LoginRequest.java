package com.example.callcenter1.dto.request;

public class LoginRequest {
    private String operatorEmail;
    private String operatorPassword;

    public LoginRequest() {}

    public LoginRequest(String operatorEmail, String operatorPassword) {
        this.operatorEmail = operatorEmail;
        this.operatorPassword = operatorPassword;
    }

    public String getOperatorEmail() { return operatorEmail; }
    public void setOperatorEmail(String operatorEmail) { this.operatorEmail = operatorEmail; }

    public String getOperatorPassword() { return operatorPassword; }
    public void setOperatorPassword(String operatorPassword) { this.operatorPassword = operatorPassword; }
}
