package com.example.callcenter1.controller;

import com.example.callcenter1.dto.request.LogRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.LogResponse;
import com.example.callcenter1.model.log.Log;
import com.example.callcenter1.service.LogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LogResponse>>> getAllLogs() {
        List<LogResponse> logs = logService.findAll()
                .stream()
                .map(LogResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm loglar listelendi", logs, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LogResponse>> getLogById(@PathVariable Integer id) {
        Log log = logService.findById(id);
        if (log == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Log bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Log bulundu", new LogResponse(log), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LogResponse>> createLog(@Valid @RequestBody LogRequest request) {
        Log saved = logService.save(request.toEntity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Log kaydedildi", new LogResponse(saved), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LogResponse>> updateLog(@PathVariable Integer id, @Valid @RequestBody LogRequest request) {
        Log updated = logService.update(id, request.toEntity());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Log bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Log güncellendi", new LogResponse(updated), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLog(@PathVariable Integer id) {
        boolean deleted = logService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Log bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Log silindi", null, null));
    }
}
