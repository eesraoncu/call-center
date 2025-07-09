package com.example.callcenter1.controller;

import com.example.callcenter1.dto.request.OperatorRequest;
import com.example.callcenter1.dto.request.SecurityApprovalRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.OperatorResponse;
import com.example.callcenter1.model.operator.Operator;
import com.example.callcenter1.service.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operators")
@Tag(name = "Operator Management", description = "Operatör yönetim API'leri")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Yeni operatör oluştur")
    @PostMapping
    public ResponseEntity<ApiResponse<OperatorResponse>> createOperator(
            @Valid @RequestBody OperatorRequest request) {

        // Email kontrolü
        if (operatorService.existsByEmail(request.getOperatorEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Bu email zaten kayıtlı", null, "EMAIL_EXISTS"));
        }

        // Telefon numarası format kontrolü
        if (!request.getOperatorPhoneNumber().matches("^[0-9]{10}$")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Telefon numarası 10 haneli olmalıdır", null, "INVALID_PHONE"));
        }

        Operator operator = new Operator();
        operator.setOperatorName(request.getOperatorName());
        operator.setOperatorSurname(request.getOperatorSurname());
        operator.setOperatorEmail(request.getOperatorEmail());
        operator.setOperatorPhoneNumber(request.getOperatorPhoneNumber());
        operator.setOperatorPassword(passwordEncoder.encode(request.getOperatorPassword()));

        // Nullable alanlar
        operator.setCustomerId(null);
        operator.setServiceId(null);

        Operator savedOperator = operatorService.save(operator);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Operatör başarıyla oluşturuldu",
                        convertToResponse(savedOperator),
                        null
                )
        );
    }

    @Operation(summary = "Güvenlik sözleşmesini onayla")
    @PostMapping("/{id}/approve-security")
    public ResponseEntity<ApiResponse<Void>> approveSecurityTerms(
            @PathVariable Integer id,
            @Valid @RequestBody SecurityApprovalRequest request) {

        Operator operator = operatorService.findById(id);
        if (operator == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Operatör bulunamadı", null, "NOT_FOUND"));
        }

        operator.setSecurityApproved(request.getApproved());
        operatorService.save(operator);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Güvenlik sözleşmesi onaylandı", null, null)
        );
    }

    @Operation(summary = "Tüm operatörleri listele")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OperatorResponse>>> getAllOperators() {
        List<OperatorResponse> operators = operatorService.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Tüm operatörler listelendi", operators, null)
        );
    }

    @Operation(summary = "ID ile operatör getir")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OperatorResponse>> getOperatorById(
            @PathVariable Integer id) {

        Operator operator = operatorService.findById(id);
        if (operator == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Operatör bulunamadı", null, "NOT_FOUND"));
        }

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Operatör bilgileri", convertToResponse(operator), null)
        );
    }

    @Operation(summary = "Operatör bilgilerini güncelle")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OperatorResponse>> updateOperator(
            @PathVariable Integer id,
            @Valid @RequestBody OperatorRequest request) {

        Operator operator = operatorService.findById(id);
        if (operator == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Operatör bulunamadı", null, "NOT_FOUND"));
        }

        // Güncelleme işlemleri
        operator.setOperatorName(request.getOperatorName());
        operator.setOperatorSurname(request.getOperatorSurname());
        operator.setOperatorEmail(request.getOperatorEmail());
        operator.setOperatorPhoneNumber(request.getOperatorPhoneNumber());

        // Şifre değişmişse güncelle
        if (request.getOperatorPassword() != null && !request.getOperatorPassword().isEmpty()) {
            operator.setOperatorPassword(passwordEncoder.encode(request.getOperatorPassword()));
        }

        Operator updatedOperator = operatorService.save(operator);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Operatör güncellendi", convertToResponse(updatedOperator), null)
        );
    }

    @Operation(summary = "Operatör sil")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOperator(
            @PathVariable Integer id) {

        if (!operatorService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Operatör bulunamadı", null, "NOT_FOUND"));
        }

        operatorService.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Operatör silindi", null, null)
        );
    }

    // Entity -> DTO dönüştürme
    private OperatorResponse convertToResponse(Operator operator) {
        OperatorResponse response = new OperatorResponse();
        response.setOperatorId(operator.getOperatorId());
        response.setOperatorName(operator.getOperatorName());
        response.setOperatorSurname(operator.getOperatorSurname());
        response.setOperatorEmail(operator.getOperatorEmail());
        response.setOperatorPhoneNumber(operator.getOperatorPhoneNumber());
        response.setRoleId(operator.getRoleId());
        response.setServiceId(operator.getServiceId());
        response.setCustomerId(operator.getCustomerId());
        response.setSecurityApproved(operator.getSecurityApproved());
        response.setCreatedAt(operator.getCreatedAt());
        return response;
    }
}