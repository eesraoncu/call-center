package com.example.callcenter1.dto.response;

import com.example.callcenter1.model.location.Address;
import com.example.callcenter1.model.location.City;
import com.example.callcenter1.model.location.District;
import com.example.callcenter1.model.location.DistrictTownshipTown;
import com.example.callcenter1.model.location.Neighbourhood;

public class AddressResponse {
    private Integer addressId;
    private Integer neighbourhoodId;
    private String neighbourhoodName;
    private Integer townshipId;
    private String townshipName;
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private String cityName;

    public AddressResponse(Address address) {
        this.addressId = address.getAddressId();
        Neighbourhood n = address.getNeighbourhood();
        this.neighbourhoodId = n.getNeighbourhoodId();
        this.neighbourhoodName = n.getNeighbourhoodName();
        DistrictTownshipTown t = n.getTownship();
        this.townshipId = t.getDistrictTownshipTownId();
        this.townshipName = t.getDistrictTownshipTownName();
        District d = t.getDistrict();
        this.districtId = d.getDistrictId();
        this.districtName = d.getDistrictName();
        City c = d.getCity();
        this.cityId = c.getCityId();
        this.cityName = c.getCityName();
    }

    // Getters and setters
    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
    public Integer getNeighbourhoodId() { return neighbourhoodId; }
    public void setNeighbourhoodId(Integer neighbourhoodId) { this.neighbourhoodId = neighbourhoodId; }
    public String getNeighbourhoodName() { return neighbourhoodName; }
    public void setNeighbourhoodName(String neighbourhoodName) { this.neighbourhoodName = neighbourhoodName; }
    public Integer getTownshipId() { return townshipId; }
    public void setTownshipId(Integer townshipId) { this.townshipId = townshipId; }
    public String getTownshipName() { return townshipName; }
    public void setTownshipName(String townshipName) { this.townshipName = townshipName; }
    public Integer getDistrictId() { return districtId; }
    public void setDistrictId(Integer districtId) { this.districtId = districtId; }
    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }
    public Integer getCityId() { return cityId; }
    public void setCityId(Integer cityId) { this.cityId = cityId; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
}