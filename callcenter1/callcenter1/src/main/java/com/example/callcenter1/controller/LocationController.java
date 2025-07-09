package com.example.callcenter1.controller;

import com.example.callcenter1.dto.request.LocationRequest;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.LocationResponse;
import com.example.callcenter1.model.location.City;
import com.example.callcenter1.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationResponse>>> getAllCities() {
        List<LocationResponse> cities = locationService.findAll()
                .stream()
                .map(LocationResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm şehirler listelendi", cities, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> getCityById(@PathVariable Integer id) {
        City city = locationService.findById(id);
        if (city == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir bulundu", new LocationResponse(city), null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LocationResponse>> createCity(@Valid @RequestBody LocationRequest request) {
        City saved = locationService.save(request.toEntity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir kaydedildi", new LocationResponse(saved), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> updateCity(@PathVariable Integer id, @Valid @RequestBody LocationRequest request) {
        City updated = locationService.update(id, request.toEntity());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir güncellendi", new LocationResponse(updated), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCity(@PathVariable Integer id) {
        boolean deleted = locationService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir silindi", null, null));
    }
}
