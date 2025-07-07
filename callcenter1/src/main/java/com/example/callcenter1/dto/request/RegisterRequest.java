package com.example.callcenter1.dto.request;

public class RegisterRequest {
    private String operatorName;
    private String operatorSurname;
    private String operatorPhoneNumber;
    private String operatorEmail;
    private String operatorPassword;

    public RegisterRequest() {}
    public RegisterRequest(String operatorName, String operatorSurname, String operatorPhoneNumber, String operatorEmail, String operatorPassword) {
         this.operatorName = operatorName;
         this.operatorSurname = operatorSurname;
         this.operatorPhoneNumber = operatorPhoneNumber;
         this.operatorEmail = operatorEmail;
         this.operatorPassword = operatorPassword;
    }

    public String getOperatorName() {return operatorName;}
    public void setOperatorName(String operatorName) {this.operatorName = operatorName;}

    public String getOperatorSurname() {return operatorSurname;}
    public void setOperatorSurname(String operatorSurname) {this.operatorSurname = operatorSurname;}

    public String getOperatorPhoneNumber() {return operatorPhoneNumber;}
    public void setOperatorPhoneNumber(String operatorPhoneNumber) {this.operatorPhoneNumber = operatorPhoneNumber;}

    public String getOperatorEmail() {return operatorEmail;}
    public void setOperatorEmail(String operatorEmail) {this.operatorEmail = operatorEmail;}

    public String getOperatorPassword() {return operatorPassword;}
    public void setOperatorPassword(String operatorPassword) {this.operatorPassword = operatorPassword;} 
}
    



