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
@Table(name = "neighbourhood")
public class Neighbourhood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "neighbourhood_id")
    private Integer neighbourhoodId;

    @Column(name = "neighbourhood_name")
    private String neighbourhoodName;

    @Column(name = "neighbourhood_explanation")
    private String neighbourhoodExplanation;

    @ManyToOne
    @JoinColumn(name = "district_township_town_id", nullable = false)
    private DistrictTownshipTown township;

    @OneToMany(mappedBy = "neighbourhood", cascade = CascadeType.ALL)
    private List<Address> addresses;

    // Getter, Setter, Constructor
    public Neighbourhood() {}

    public Neighbourhood(String neighbourhoodName, DistrictTownshipTown township) {
        this.neighbourhoodName = neighbourhoodName;
        this.township = township;
    }

    public Integer getNeighbourhoodId() { return neighbourhoodId; }
    public void setNeighbourhoodId(Integer id) { this.neighbourhoodId = id; }

    public String getNeighbourhoodName() { return neighbourhoodName; }
    public void setNeighbourhoodName(String name) { this.neighbourhoodName = name; }

    public String getNeighbourhoodExplanation() { return neighbourhoodExplanation; }
    public void setNeighbourhoodExplanation(String explanation) { this.neighbourhoodExplanation = explanation; }

    public DistrictTownshipTown getTownship() { return township; }
    public void setTownship(DistrictTownshipTown township) { this.township = township; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
}