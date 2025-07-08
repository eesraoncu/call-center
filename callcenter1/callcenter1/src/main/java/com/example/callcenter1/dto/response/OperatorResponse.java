package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class OperatorResponse {
    private Integer operatorId;
    private String operatorName;
    private String operatorSurname;
    private String operatorPhoneNumber;
    private String operatorEmail;
    private Integer roleId;
}
