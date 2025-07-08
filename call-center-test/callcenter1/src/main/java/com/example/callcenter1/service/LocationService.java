package com.example.callcenter1.service;

import com.example.callcenter1.model.location.Address;
import com.example.callcenter1.model.location.City;
import com.example.callcenter1.model.location.District;
import com.example.callcenter1.model.location.DistrictTownshipTown;
import com.example.callcenter1.model.location.Neighbourhood;
import com.example.callcenter1.repository.location.AddressRepository;
import com.example.callcenter1.repository.location.CityRepository;
import com.example.callcenter1.repository.location.DistrictRepository;
import com.example.callcenter1.repository.location.DistrictTownshipTownRepository;
import com.example.callcenter1.repository.location.NeighbourhoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationService {
    @Autowired private AddressRepository addressRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired private DistrictRepository districtRepository;
    @Autowired private DistrictTownshipTownRepository districtTownshipTownRepository;
    @Autowired private NeighbourhoodRepository neighbourhoodRepository;

    // Şehir işlemleri örnek
    public List<City> getAllCities() { return cityRepository.findAll(); }
    public City saveCity(City city) { return cityRepository.save(city); }
    public void deleteCity(Integer id) { cityRepository.deleteById(id); }
    // Diğer entity'ler için de benzer CRUD metotları eklenebilir
}
