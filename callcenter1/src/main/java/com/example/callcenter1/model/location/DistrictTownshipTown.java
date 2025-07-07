package com.example.callcenter1.model.location;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "district_township_town")
public class DistrictTownshipTown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_township_town_id")
    private Integer districtTownshipTownId;

    @Column(name = "district_township_town_name", nullable = false)
    private String districtTownshipTownName;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @OneToMany(mappedBy = "township", cascade = CascadeType.ALL)
    private List<Neighbourhood> neighbourhoods;

    public DistrictTownshipTown() {}

    public DistrictTownshipTown(String districtTownshipTownName, District district) {
        this.districtTownshipTownName = districtTownshipTownName;
        this.district = district;
    }

    public Integer getDistrictTownshipTownId() { return districtTownshipTownId; }
    public void setDistrictTownshipTownId(Integer id) { this.districtTownshipTownId = id; }

    public String getDistrictTownshipTownName() { return districtTownshipTownName; }
    public void setDistrictTownshipTownName(String name) { this.districtTownshipTownName = name; }

    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }

    public List<Neighbourhood> getNeighbourhoods() { return neighbourhoods; }
    public void setNeighbourhoods(List<Neighbourhood> neighbourhoods) { this.neighbourhoods = neighbourhoods; }
}
