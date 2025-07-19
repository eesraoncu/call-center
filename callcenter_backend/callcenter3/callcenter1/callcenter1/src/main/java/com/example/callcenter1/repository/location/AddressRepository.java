package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.Address; // Mustafa'nın oluşturacağı model sınıfı
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    // List<Address> findByNeighbourhoodId(Integer neighbourhoodId);
}