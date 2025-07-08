package com.example.callcenter1.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorRequest {
    private String operatorName;
    private String operatorSurname;
    private String operatorPhoneNumber;
    private String operatorEmail;
    private String operatorPassword;
    private Integer roleId;
}
