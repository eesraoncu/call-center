package com.example.callcenter1.service;

import com.example.callcenter1.model.location.City;
import com.example.callcenter1.repository.location.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Integer id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public City update(Integer id, City city) {
        if (cityRepository.existsById(id)) {
            city.setCityId(id);
            return cityRepository.save(city);
        }
        return null;
    }

    public boolean delete(Integer id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
