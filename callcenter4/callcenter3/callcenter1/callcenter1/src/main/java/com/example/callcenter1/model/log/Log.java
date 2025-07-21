package com.example.callcenter1.model.log;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.model.customer.Customer;


@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "log_description", nullable = false,columnDefinition = "text")
    private String logDescription;

    @Column(name = "operator_id", nullable = true)
    private Integer operatorId;

    @Column(name = "customer_id", nullable = true)
    private Integer customerId;

    @Column(name = "log_datetime", nullable = false)
    private java.time.LocalDateTime logDatetime;

    @Column(name = "request_method",columnDefinition = "text")
    private String method;

    @Column(name = "request_uri",columnDefinition = "text")
    private String uri;

    @Column(name = "request_body",columnDefinition = "text")
    private String requestBody;

    @Column(name = "response_body",columnDefinition = "text")
    private String responseBody;

    @Column(name = "response_status")
    private Integer responseStatus;

    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "operator_id", insertable = false, updatable = false)
    private Operator operator;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customer customer;


    // Getter ve Setter'lar

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }



    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public Integer getOperatorId() { return operatorId; }
    public void setOperatorId(Integer operatorId) { this.operatorId = operatorId; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getResponseStatus() { return responseStatus; }
    public void setResponseStatus(Integer responseStatus) { this.responseStatus = responseStatus; }
    public java.time.LocalDateTime getLogDatetime() { return logDatetime; }
    public void setLogDatetime(java.time.LocalDateTime logDatetime) { this.logDatetime = logDatetime; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getRequestBody() { return requestBody; }
    public void setRequestBody(String requestBody) { this.requestBody = requestBody; }
    public String getResponseBody() { return responseBody; }
    public void setResponseBody(String responseBody) { this.responseBody = responseBody; }
    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }


}
