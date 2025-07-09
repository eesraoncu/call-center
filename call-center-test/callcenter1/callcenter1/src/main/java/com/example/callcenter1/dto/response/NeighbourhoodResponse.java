package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.location.Neighbourhood;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeighbourhoodResponse {
    private Integer neighbourhoodId;
    private String neighbourhoodName;
    private String neighbourhoodExplanation;
    private Integer townshipId;
    private String townshipName;
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private String cityName;

    public NeighbourhoodResponse(Neighbourhood neighbourhood) {
        this.neighbourhoodId = neighbourhood.getNeighbourhoodId();
        this.neighbourhoodName = neighbourhood.getNeighbourhoodName();
        this.neighbourhoodExplanation = neighbourhood.getNeighbourhoodExplanation();
        this.townshipId = neighbourhood.getTownship().getDistrictTownshipTownId();
        this.townshipName = neighbourhood.getTownship().getDistrictTownshipTownName();
        this.districtId = neighbourhood.getTownship().getDistrict().getDistrictId();
        this.districtName = neighbourhood.getTownship().getDistrict().getDistrictName();
        this.cityId = neighbourhood.getTownship().getDistrict().getCity().getCityId();
        this.cityName = neighbourhood.getTownship().getDistrict().getCity().getCityName();
    }
} 