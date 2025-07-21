package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findByCityCityId(Integer cityId);
}
