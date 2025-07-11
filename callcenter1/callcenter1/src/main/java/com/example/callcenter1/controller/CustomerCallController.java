package com.example.callcenter1.controller;

import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.CustomerCallDurationResponse;
import com.example.callcenter1.model.call.CustomerCall;
import com.example.callcenter1.service.CustomerCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-calls")
public class CustomerCallController {

    @Autowired
    private CustomerCallService customerCallService;

    // Müşterinin tüm çağrı sürelerini getir
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerCallDurationResponse>>> getCustomerCallDurations(@PathVariable Integer customerId) {
        List<CustomerCallDurationResponse> durations = customerCallService.getCustomerCallDurations(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri çağrı süreleri getirildi", durations, null));
    }

    // Müşterinin toplam çağrı süresini getir
    @GetMapping("/customer/{customerId}/total-duration")
    public ResponseEntity<ApiResponse<Integer>> getCustomerTotalCallDuration(@PathVariable Integer customerId) {
        Integer totalDuration = customerCallService.getCustomerTotalCallDuration(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri toplam çağrı süresi getirildi", totalDuration, null));
    }

    // Müşterinin ortalama çağrı süresini getir
    @GetMapping("/customer/{customerId}/average-duration")
    public ResponseEntity<ApiResponse<Double>> getCustomerAverageCallDuration(@PathVariable Integer customerId) {
        Double averageDuration = customerCallService.getCustomerAverageCallDuration(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri ortalama çağrı süresi getirildi", averageDuration, null));
    }

    // Tüm müşterilerin çağrı sürelerini getir
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CustomerCallDurationResponse>>> getAllCustomerCallDurations() {
        List<CustomerCallDurationResponse> allDurations = customerCallService.getAllCustomerCallDurations();
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm müşteri çağrı süreleri getirildi", allDurations, null));
    }

    // Müşteri-Çağrı ilişkisi oluştur
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerCall>> createCustomerCall(
            @RequestParam Integer customerId,
            @RequestParam Integer callId) {
        CustomerCall customerCall = customerCallService.createCustomerCall(customerId, callId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri-Çağrı ilişkisi oluşturuldu", customerCall, null));
    }

    // Müşteri-Çağrı ilişkisi sil
    @DeleteMapping("/customer/{customerId}/call/{callId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerCall(
            @PathVariable Integer customerId,
            @PathVariable Integer callId) {
        customerCallService.deleteCustomerCall(customerId, callId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Müşteri-Çağrı ilişkisi silindi", null, null));
    }

    // Belirli çağrının müşterilerini getir
    @GetMapping("/call/{callId}")
    public ResponseEntity<ApiResponse<List<CustomerCallDurationResponse>>> getCallCustomers(@PathVariable Integer callId) {
        // Bu endpoint için service'e metod eklenebilir
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı müşterileri getirildi", null, null));
    }
} 