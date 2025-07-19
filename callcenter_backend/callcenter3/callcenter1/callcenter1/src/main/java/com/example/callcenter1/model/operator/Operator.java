package com.example.callcenter1.model.operator;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
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

    @Column(name = "operator_email", nullable = false, unique = true)
    private String operatorEmail;

    @Column(name = "operator_phone_number", nullable = false, length = 10)
    private String operatorPhoneNumber;

    @Column(name = "operator_password", nullable = false)
    private String operatorPassword;

    @Column(name = "role_id", nullable = false)
    private Integer roleId = 2; // Default: Operator rolü


    @Column(name = "customer_id")
    private Integer customerId; // Nullable

    @Column(name = "security_approved", nullable = false)
    private Boolean securityApproved = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}