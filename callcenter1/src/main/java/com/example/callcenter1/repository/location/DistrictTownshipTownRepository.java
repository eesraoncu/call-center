package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.DistrictTownshipTown; // Mustafa'nın oluşturacağı model sınıfı
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface DistrictTownshipTownRepository extends JpaRepository<DistrictTownshipTown, Integer> {
    // Optional<DistrictTownshipTown> findByDistrictTownshipTownNameAndDistrictId(String dttName, Integer districtId);
    // List<DistrictTownshipTown> findByDistrictId(Integer districtId);
}