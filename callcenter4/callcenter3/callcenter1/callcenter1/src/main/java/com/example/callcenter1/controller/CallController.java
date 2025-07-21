package com.example.callcenter1.controller;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.callcenter1.dto.request.CallRequest;
import com.example.callcenter1.dto.request.LogRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.CallResponse;
import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.model.call.CustomerCall;
import com.example.callcenter1.repository.call.CustomerCallRepository;
import com.example.callcenter1.service.CallService;
import com.example.callcenter1.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/calls")
public class CallController {

    @Autowired
    private CallService callService;

    @Autowired
    private LogService logService;

    @Autowired
    private CustomerCallRepository customerCallRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CallResponse>>> getAllCalls() {
        List<CallRecords> calls = callService.findAll();
        List<CallResponse> responses = calls.stream().map(call -> {
            CustomerCall cc = customerCallRepository.findByCallId(call.getCallId());
            Integer customerId = cc != null ? cc.getCustomerId() : null;
            return new CallResponse(call, customerId);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm çağrılar listelendi", responses, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CallResponse>> getCallById(@PathVariable Integer id) {
        CallRecords call = callService.findById(id);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (call == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Çağrı bulunamadı", null, "NOT_FOUND"));
        }
        CustomerCall cc = customerCallRepository.findByCallId(call.getCallId());
        Integer customerId = cc != null ? cc.getCustomerId() : null;
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı bulundu", new CallResponse(call, customerId), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CallResponse>> createCall(@Valid @RequestBody CallRequest request) {
        CallRecords saved = callService.save(request.toEntity(), request.getCustomerId());
        CustomerCall cc = customerCallRepository.findByCallId(saved.getCallId());
        Integer customerId = cc != null ? cc.getCustomerId() : request.getCustomerId();
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı oluşturuldu", new CallResponse(saved, customerId), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CallResponse>> updateCall(@PathVariable Integer id, @Valid @RequestBody CallRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CallRecords updated = callService.update(id, request.toEntity(), request.getCustomerId());
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Çağrı bulunamadı", null, "NOT_FOUND"));
            }
            CustomerCall cc = customerCallRepository.findByCallId(updated.getCallId());
            Integer customerId = cc != null ? cc.getCustomerId() : request.getCustomerId();
            return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı güncellendi", new CallResponse(updated, customerId), null));
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Tarih veya zaman formatı hatalı", null, "INVALID_FORMAT"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Çağrı güncellenirken hata oluştu: " + e.getMessage(), null, "INTERNAL_ERROR"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCall(@PathVariable Integer id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean deleted = callService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Çağrı bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı silindi", null, null));
    }
}
