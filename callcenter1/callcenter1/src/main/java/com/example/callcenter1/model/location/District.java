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
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name", nullable = false)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<DistrictTownshipTown> townships;

    // Getter, Setter, Constructor
    public District() {}

    public District(String districtName, City city) {
        this.districtName = districtName;
        this.city = city;
    }

    public Integer getDistrictId() { return districtId; }
    public void setDistrictId(Integer districtId) { this.districtId = districtId; }

    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public List<DistrictTownshipTown> getTownships() { return townships; }
    public void setTownships(List<DistrictTownshipTown> townships) { this.townships = townships; }
}
