package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.location.District;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictResponse {
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private String cityName;

    public DistrictResponse(District district) {
        this.districtId = district.getDistrictId();
        this.districtName = district.getDistrictName();
        this.cityId = district.getCity().getCityId();
        this.cityName = district.getCity().getCityName();
    }
} 