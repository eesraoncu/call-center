package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.DistrictTownshipTown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictTownshipTownRepository extends JpaRepository<DistrictTownshipTown, Integer> {
    List<DistrictTownshipTown> findByDistrictDistrictId(Integer districtId);
}