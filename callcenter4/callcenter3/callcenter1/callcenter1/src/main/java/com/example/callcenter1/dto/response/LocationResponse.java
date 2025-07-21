package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.location.City;
import lombok.Data;

@Data
public class LocationResponse {
    private Integer cityId;
    private String cityName;

    public LocationResponse(City city) {
        this.cityId = city.getCityId();
        this.cityName = city.getCityName();
    }
}
