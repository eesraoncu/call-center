package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.log.Log;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {
    @NotBlank(message = "Log açıklaması boş olamaz")
    private String logDescription;
    private Integer operatorId;
    private Integer customerId;
    private String method;
    private String uri;
    private String requestBody;
    private String responseBody;
    private java.time.LocalDateTime logDatetime;
    private Integer responseStatus;

    public Log toEntity() {
        Log log = new Log();
        log.setLogDescription(this.logDescription);
        log.setOperatorId(this.operatorId);
        log.setCustomerId(this.customerId);
        log.setMethod(this.method);
        log.setUri(this.uri);
        log.setRequestBody(this.requestBody);
        log.setResponseBody(this.responseBody);
        log.setLogDatetime(this.logDatetime);
        log.setResponseStatus(this.responseStatus);
        return log;
    }
}
