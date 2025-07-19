package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.location.DistrictTownshipTown;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TownshipResponse {
    private Integer townshipId;
    private String townshipName;
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private String cityName;

    public TownshipResponse(DistrictTownshipTown township) {
        this.townshipId = township.getDistrictTownshipTownId();
        this.townshipName = township.getDistrictTownshipTownName();
        this.districtId = township.getDistrict().getDistrictId();
        this.districtName = township.getDistrict().getDistrictName();
        this.cityId = township.getDistrict().getCity().getCityId();
        this.cityName = township.getDistrict().getCity().getCityName();
    }
} 