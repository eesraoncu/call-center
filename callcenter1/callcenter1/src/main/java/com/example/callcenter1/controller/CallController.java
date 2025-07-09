package com.example.callcenter1.controller;

import com.example.callcenter1.dto.request.CallRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.CallResponse;
import com.example.callcenter1.model.call.CallRecords;
import com.example.callcenter1.service.CallService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calls")
public class CallController {

    @Autowired
    private CallService callService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CallResponse>>> getAllCalls() {
        List<CallResponse> calls = callService.findAll()
                .stream()
                .map(CallResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm çağrılar listelendi", calls, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CallResponse>> getCallById(@PathVariable Integer id) {
        CallRecords call = callService.findById(id);
        if (call == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Çağrı bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı bulundu", new CallResponse(call), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CallResponse>> createCall(@Valid @RequestBody CallRequest request) {
        CallRecords saved = callService.save(request.toEntity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı oluşturuldu", new CallResponse(saved), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CallResponse>> updateCall(@PathVariable Integer id, @Valid @RequestBody CallRequest request) {
        CallRecords updated = callService.update(id, request.toEntity());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Çağrı bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı güncellendi", new CallResponse(updated), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCall(@PathVariable Integer id) {
        boolean deleted = callService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Çağrı bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Çağrı silindi", null, null));
    }
}
