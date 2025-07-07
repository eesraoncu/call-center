package com.example.callcenter1.model.operator;

import jakarta.persistence.*;

@Entity
@Table(name = "operator")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operator_id")
    private Integer operatorId;

    @Column(name = "operator_name", nullable = false, length = 30)
    private String operatorName;

    @Column(name = "operator_surname", nullable = false, length = 30)
    private String operatorSurname;

    @Column(name = "operator_phone_number", nullable = false)
    private String operatorPhoneNumber;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "operator_password")
    private String operatorPassword;

    @Column(name = "id", nullable = false)
    private Long id;

    // Getter ve Setter'lar
    public Integer getOperatorId() { return operatorId; }
    public void setOperatorId(Integer operatorId) { this.operatorId = operatorId; }

    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }

    public String getOperatorSurname() { return operatorSurname; }
    public void setOperatorSurname(String operatorSurname) { this.operatorSurname = operatorSurname; }

    public String getOperatorPhoneNumber() { return operatorPhoneNumber; }
    public void setOperatorPhoneNumber(String operatorPhoneNumber) { this.operatorPhoneNumber = operatorPhoneNumber; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getOperatorPassword() { return operatorPassword; }
    public void setOperatorPassword(String operatorPassword) { this.operatorPassword = operatorPassword; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
} 