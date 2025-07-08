package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.District; // Mustafa'nın oluşturacağı model sınıfı
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    // List<District> findByCityId(Integer cityId);
    // Optional<District> findByDistrictNameAndCityId(String name);
}
