package com.example.callcenter1.dto.request;

import com.example.callcenter1.model.location.City;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    private Integer cityId;

    @NotBlank(message = "Şehir adı boş olamaz")
    private String cityName;

    private Integer districtId;
    private String districtName;
    private Integer districtTownshipTownId;
    private String districtTownshipTownName;
    private Integer neighbourhoodId;
    private String neighbourhoodName;


    public City toEntity() {
        City city = new City();
        city.setCityId(this.cityId);
        city.setCityName(this.cityName);
        // Diğer alanlar City entity içinde yoksa, oraya göre uyarlaman gerekebilir.
        return city;
    }
}
