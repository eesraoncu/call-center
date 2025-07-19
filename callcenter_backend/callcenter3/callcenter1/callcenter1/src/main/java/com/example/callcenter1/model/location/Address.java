package com.example.callcenter1.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "neighbourhood_id", nullable = false)
    private Neighbourhood neighbourhood;

    // İleride Customer ile ilişki kurulacaksa buraya eklenebilir

    // Getter, Setter, Constructor
    public Address() {}

    public Address(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }

    public Neighbourhood getNeighbourhood() { return neighbourhood; }
    public void setNeighbourhood(Neighbourhood neighbourhood) { this.neighbourhood = neighbourhood; }
}
