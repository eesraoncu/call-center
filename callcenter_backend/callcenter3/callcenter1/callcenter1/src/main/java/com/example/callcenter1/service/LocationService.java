package com.example.callcenter1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class LocationService {
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private DistrictRepository districtRepository;
    
    @Autowired
    private DistrictTownshipTownRepository townshipRepository;
    
    @Autowired
    private NeighbourhoodRepository neighbourhoodRepository;

    @Autowired
    private AddressRepository addressRepository;

    // Şehir işlemleri
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public City findCityById(Integer id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Integer id, City city) {
        if (cityRepository.existsById(id)) {
            city.setCityId(id);
            return cityRepository.save(city);
        }
        return null;
    }

    public boolean deleteCity(Integer id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // İlçe işlemleri
    public List<District> findDistrictsByCityId(Integer cityId) {
        return districtRepository.findByCityCityId(cityId);
    }

    public District findDistrictById(Integer id) {
        Optional<District> district = districtRepository.findById(id);
        return district.orElse(null);
    }

    public District saveDistrict(District district) {
        return districtRepository.save(district);
    }

    public District updateDistrict(Integer id, District district) {
        if (districtRepository.existsById(id)) {
            district.setDistrictId(id);
            return districtRepository.save(district);
        }
        return null;
    }

    public boolean deleteDistrict(Integer id) {
        if (districtRepository.existsById(id)) {
            districtRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Belde/Köy işlemleri
    public List<DistrictTownshipTown> findTownshipsByDistrictId(Integer districtId) {
        return townshipRepository.findByDistrictDistrictId(districtId);
    }

    public DistrictTownshipTown findTownshipById(Integer id) {
        Optional<DistrictTownshipTown> township = townshipRepository.findById(id);
        return township.orElse(null);
    }

    public DistrictTownshipTown saveTownship(DistrictTownshipTown township) {
        return townshipRepository.save(township);
    }

    public DistrictTownshipTown updateTownship(Integer id, DistrictTownshipTown township) {
        if (townshipRepository.existsById(id)) {
            township.setDistrictTownshipTownId(id);
            return townshipRepository.save(township);
        }
        return null;
    }

    public boolean deleteTownship(Integer id) {
        if (townshipRepository.existsById(id)) {
            townshipRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Mahalle işlemleri
    public List<Neighbourhood> findNeighbourhoodsByTownshipId(Integer townshipId) {
        return neighbourhoodRepository.findByTownshipDistrictTownshipTownId(townshipId);
    }

    public Neighbourhood findNeighbourhoodById(Integer id) {
        Optional<Neighbourhood> neighbourhood = neighbourhoodRepository.findById(id);
        return neighbourhood.orElse(null);
    }

    public Neighbourhood saveNeighbourhood(Neighbourhood neighbourhood) {
        return neighbourhoodRepository.save(neighbourhood);
    }

    public Neighbourhood updateNeighbourhood(Integer id, Neighbourhood neighbourhood) {
        if (neighbourhoodRepository.existsById(id)) {
            neighbourhood.setNeighbourhoodId(id);
            return neighbourhoodRepository.save(neighbourhood);
        }
        return null;
    }

    public boolean deleteNeighbourhood(Integer id) {
        if (neighbourhoodRepository.existsById(id)) {
            neighbourhoodRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Adres işlemleri
    public Address findAddressById(Integer id) {
        return addressRepository.findById(id).orElse(null);
    }

    // Mahalle id'sine göre adresi bul, yoksa oluştur
    public Address findOrCreateAddressByNeighbourhoodId(Integer neighbourhoodId) {
        List<Address> addresses = addressRepository.findAll();
        for (Address address : addresses) {
            if (address.getNeighbourhood() != null && address.getNeighbourhood().getNeighbourhoodId().equals(neighbourhoodId)) {
                return address;
            }
        }
        // Yoksa oluştur
        Neighbourhood neighbourhood = findNeighbourhoodById(neighbourhoodId);
        if (neighbourhood == null) return null;
        Address newAddress = new Address();
        newAddress.setNeighbourhood(neighbourhood);
        return addressRepository.save(newAddress);
    }

    // Geriye uyumluluk için eski metodlar
    public List<City> findAll() {
        return findAllCities();
    }

    public City findById(Integer id) {
        return findCityById(id);
    }

    public City save(City city) {
        return saveCity(city);
    }

    public City update(Integer id, City city) {
        return updateCity(id, city);
    }

    public boolean delete(Integer id) {
        return deleteCity(id);
    }
}