package com.example.callcenter1.dto.response;

import lombok.Data;

@Data
public class LocationResponse {
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
    private Integer districtTownshipTownId;
    private String districtTownshipTownName;
    private Integer neighbourhoodId;
    private String neighbourhoodName;
    private String neighbourhoodExplanation;
    private Integer addressId; 
}
