package com.example.callcenter1.repository.location;

import com.example.callcenter1.model.location.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    // Optional<City> findByCityName(String cityName);
}