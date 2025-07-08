package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.Neighbourhood; // Mustafa'nın oluşturacağı model sınıfı
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, Integer> {
        // List<Neighbourhood> findByDistrictTownshipTownId(Integer dttId);
    // Optional<Neighbourhood> findByNeighbourhoodNameAndDistrictTownshipTownId(String neighbourhoodName, Integer dttId);
}