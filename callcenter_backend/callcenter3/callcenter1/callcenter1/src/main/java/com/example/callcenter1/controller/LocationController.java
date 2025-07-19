package com.example.callcenter1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.callcenter1.dto.request.CreateAddressRequest;
import com.example.callcenter1.dto.request.LocationRequest;
import com.example.callcenter1.dto.response.AddressResponse;
import com.example.callcenter1.dto.response.ApiResponse;
import com.example.callcenter1.dto.response.DistrictResponse;
import com.example.callcenter1.dto.response.LocationResponse;
import com.example.callcenter1.dto.response.NeighbourhoodResponse;
import com.example.callcenter1.dto.response.TownshipResponse;
import com.example.callcenter1.model.location.Address;
import com.example.callcenter1.model.location.City;
import com.example.callcenter1.model.location.District;
import com.example.callcenter1.model.location.DistrictTownshipTown;
import com.example.callcenter1.model.location.Neighbourhood;
import com.example.callcenter1.service.LocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Şehir endpoint'leri
    @GetMapping("/cities")
    public ResponseEntity<ApiResponse<List<LocationResponse>>> getAllCities() {
        List<LocationResponse> cities = locationService.findAllCities()
                .stream()
                .map(LocationResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Tüm şehirler listelendi", cities, null));
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> getCityById(@PathVariable Integer id) {
        City city = locationService.findCityById(id);
        if (city == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir bulundu", new LocationResponse(city), null));
    }

    @PostMapping("/cities")
    public ResponseEntity<ApiResponse<LocationResponse>> createCity(@Valid @RequestBody LocationRequest request) {
        City saved = locationService.saveCity(request.toEntity());
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir kaydedildi", new LocationResponse(saved), null));
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> updateCity(@PathVariable Integer id, @Valid @RequestBody LocationRequest request) {
        City updated = locationService.updateCity(id, request.toEntity());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir güncellendi", new LocationResponse(updated), null));
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCity(@PathVariable Integer id) {
        boolean deleted = locationService.deleteCity(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Şehir silindi", null, null));
    }

    // İlçe endpoint'leri
    @GetMapping("/cities/{cityId}/districts")
    public ResponseEntity<ApiResponse<List<DistrictResponse>>> getDistrictsByCityId(@PathVariable Integer cityId) {
        List<DistrictResponse> districts = locationService.findDistrictsByCityId(cityId)
                .stream()
                .map(DistrictResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "İlçeler listelendi", districts, null));
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<ApiResponse<DistrictResponse>> getDistrictById(@PathVariable Integer id) {
        District district = locationService.findDistrictById(id);
        if (district == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "İlçe bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "İlçe bulundu", new DistrictResponse(district), null));
    }

    @PostMapping("/districts")
    public ResponseEntity<ApiResponse<DistrictResponse>> createDistrict(@Valid @RequestBody LocationRequest request) {
        City city = locationService.findCityById(request.getCityId());
        if (city == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "CITY_NOT_FOUND"));
        }

        District district = new District();
        district.setDistrictName(request.getDistrictName());
        district.setCity(city);

        District saved = locationService.saveDistrict(district);
        return ResponseEntity.ok(new ApiResponse<>(true, "İlçe kaydedildi", new DistrictResponse(saved), null));
    }

    @PutMapping("/districts/{id}")
    public ResponseEntity<ApiResponse<DistrictResponse>> updateDistrict(@PathVariable Integer id, @Valid @RequestBody LocationRequest request) {
        District existingDistrict = locationService.findDistrictById(id);
        if (existingDistrict == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "İlçe bulunamadı", null, "NOT_FOUND"));
        }

        if (request.getCityId() != null) {
            City city = locationService.findCityById(request.getCityId());
            if (city == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "Şehir bulunamadı", null, "CITY_NOT_FOUND"));
            }
            existingDistrict.setCity(city);
        }

        if (request.getDistrictName() != null) {
            existingDistrict.setDistrictName(request.getDistrictName());
        }

        District updated = locationService.updateDistrict(id, existingDistrict);
        return ResponseEntity.ok(new ApiResponse<>(true, "İlçe güncellendi", new DistrictResponse(updated), null));
    }

    @DeleteMapping("/districts/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDistrict(@PathVariable Integer id) {
        boolean deleted = locationService.deleteDistrict(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "İlçe bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "İlçe silindi", null, null));
    }

    // Belde/Köy endpoint'leri
    @GetMapping("/districts/{districtId}/townships")
    public ResponseEntity<ApiResponse<List<TownshipResponse>>> getTownshipsByDistrictId(@PathVariable Integer districtId) {
        List<TownshipResponse> townships = locationService.findTownshipsByDistrictId(districtId)
                .stream()
                .map(TownshipResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Beldeler/Köyler listelendi", townships, null));
    }

    @GetMapping("/townships/{id}")
    public ResponseEntity<ApiResponse<TownshipResponse>> getTownshipById(@PathVariable Integer id) {
        DistrictTownshipTown township = locationService.findTownshipById(id);
        if (township == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Belde/Köy bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Belde/Köy bulundu", new TownshipResponse(township), null));
    }

    @PostMapping("/townships")
    public ResponseEntity<ApiResponse<TownshipResponse>> createTownship(@Valid @RequestBody LocationRequest request) {
        District district = locationService.findDistrictById(request.getDistrictId());
        if (district == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "İlçe bulunamadı", null, "DISTRICT_NOT_FOUND"));
        }

        DistrictTownshipTown township = new DistrictTownshipTown();
        township.setDistrictTownshipTownName(request.getDistrictTownshipTownName());
        township.setDistrict(district);

        DistrictTownshipTown saved = locationService.saveTownship(township);
        return ResponseEntity.ok(new ApiResponse<>(true, "Belde/Köy kaydedildi", new TownshipResponse(saved), null));
    }

    @PutMapping("/townships/{id}")
    public ResponseEntity<ApiResponse<TownshipResponse>> updateTownship(@PathVariable Integer id, @Valid @RequestBody LocationRequest request) {
        DistrictTownshipTown existingTownship = locationService.findTownshipById(id);
        if (existingTownship == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Belde/Köy bulunamadı", null, "NOT_FOUND"));
        }

        if (request.getDistrictId() != null) {
            District district = locationService.findDistrictById(request.getDistrictId());
            if (district == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "İlçe bulunamadı", null, "DISTRICT_NOT_FOUND"));
            }
            existingTownship.setDistrict(district);
        }

        if (request.getDistrictTownshipTownName() != null) {
            existingTownship.setDistrictTownshipTownName(request.getDistrictTownshipTownName());
        }

        DistrictTownshipTown updated = locationService.updateTownship(id, existingTownship);
        return ResponseEntity.ok(new ApiResponse<>(true, "Belde/Köy güncellendi", new TownshipResponse(updated), null));
    }

    @DeleteMapping("/townships/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTownship(@PathVariable Integer id) {
        boolean deleted = locationService.deleteTownship(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Belde/Köy bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Belde/Köy silindi", null, null));
    }

    // Mahalle endpoint'leri
    @GetMapping("/townships/{townshipId}/neighbourhoods")
    public ResponseEntity<ApiResponse<List<NeighbourhoodResponse>>> getNeighbourhoodsByTownshipId(@PathVariable Integer townshipId) {
        List<NeighbourhoodResponse> neighbourhoods = locationService.findNeighbourhoodsByTownshipId(townshipId)
                .stream()
                .map(NeighbourhoodResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Mahalleler listelendi", neighbourhoods, null));
    }

    @GetMapping("/neighbourhoods/{id}")
    public ResponseEntity<ApiResponse<NeighbourhoodResponse>> getNeighbourhoodById(@PathVariable Integer id) {
        Neighbourhood neighbourhood = locationService.findNeighbourhoodById(id);
        if (neighbourhood == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Mahalle bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Mahalle bulundu", new NeighbourhoodResponse(neighbourhood), null));
    }

    @PostMapping("/neighbourhoods")
    public ResponseEntity<ApiResponse<NeighbourhoodResponse>> createNeighbourhood(@Valid @RequestBody LocationRequest request) {
        DistrictTownshipTown township = locationService.findTownshipById(request.getDistrictTownshipTownId());
        if (township == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Belde/Köy bulunamadı", null, "TOWNSHIP_NOT_FOUND"));
        }

        Neighbourhood neighbourhood = new Neighbourhood();
        neighbourhood.setNeighbourhoodName(request.getNeighbourhoodName());
        neighbourhood.setTownship(township);

        Neighbourhood saved = locationService.saveNeighbourhood(neighbourhood);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mahalle kaydedildi", new NeighbourhoodResponse(saved), null));
    }

    @PutMapping("/neighbourhoods/{id}")
    public ResponseEntity<ApiResponse<NeighbourhoodResponse>> updateNeighbourhood(@PathVariable Integer id, @Valid @RequestBody LocationRequest request) {
        Neighbourhood existingNeighbourhood = locationService.findNeighbourhoodById(id);
        if (existingNeighbourhood == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Mahalle bulunamadı", null, "NOT_FOUND"));
        }

        if (request.getDistrictTownshipTownId() != null) {
            DistrictTownshipTown township = locationService.findTownshipById(request.getDistrictTownshipTownId());
            if (township == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "Belde/Köy bulunamadı", null, "TOWNSHIP_NOT_FOUND"));
            }
            existingNeighbourhood.setTownship(township);
        }

        if (request.getNeighbourhoodName() != null) {
            existingNeighbourhood.setNeighbourhoodName(request.getNeighbourhoodName());
        }

        /*if (request.getNeighbourhoodExplanation() != null) {
            existingNeighbourhood.setNeighbourhoodExplanation(request.getNeighbourhoodExplanation());
        }*/

        Neighbourhood updated = locationService.updateNeighbourhood(id, existingNeighbourhood);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mahalle güncellendi", new NeighbourhoodResponse(updated), null));
    }

    @DeleteMapping("/neighbourhoods/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNeighbourhood(@PathVariable Integer id) {
        boolean deleted = locationService.deleteNeighbourhood(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Mahalle bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Mahalle silindi", null, null));
    }

    // Adres bilgilerini tek bir id ile çekmek için endpoint
    @GetMapping("/addresses/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddressById(@PathVariable Integer id) {
        Address address = locationService.findAddressById(id);
        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Adres bulunamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Adres bulundu", new AddressResponse(address), null));
    }

    // Mahalle id'sine göre adresi getir (adres yoksa oluştur)
    @GetMapping("/addresses/by-neighbourhood/{neighbourhoodId}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddressByNeighbourhoodId(@PathVariable Integer neighbourhoodId) {
        Address address = locationService.findOrCreateAddressByNeighbourhoodId(neighbourhoodId);
        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Adres bulunamadı ve oluşturulamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Adres bulundu", new AddressResponse(address), null));
    }

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(@RequestBody CreateAddressRequest request) {
        Address address = locationService.findOrCreateAddressByNeighbourhoodId(request.getNeighbourhoodId());
        if (address == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Adres oluşturulamadı", null, "NOT_FOUND"));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Adres oluşturuldu", new AddressResponse(address), null));
    }

    // Geriye uyumluluk için eski endpoint'ler
    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationResponse>>> getAllCitiesLegacy() {
        return getAllCities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> getCityByIdLegacy(@PathVariable Integer id) {
        return getCityById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LocationResponse>> createCityLegacy(@Valid @RequestBody LocationRequest request) {
        return createCity(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponse>> updateCityLegacy(@PathVariable Integer id, @Valid @RequestBody LocationRequest request) {
        return updateCity(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCityLegacy(@PathVariable Integer id) {
        return deleteCity(id);
    }
}