package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.Neighbourhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, Integer> {
    List<Neighbourhood> findByTownshipDistrictTownshipTownId(Integer townshipId);
}