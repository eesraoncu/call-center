package com.example.callcenter1.model.location;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // bu sınıfın bir veri tabanı tablosu olduğunu belirtir
@Table(name = "city") // hangi tabloya karşılık geldiğini belirtir
public class City {
    @Id // birincil anahtarını belirtmek için kullanılır
    @GeneratedValue(strategy = GenerationType.IDENTITY) // veritabanında otomatik artan bir değer üretir
    @Column(name = "city_id") // veritabanındaki sütun adını belirtir
    private Integer cityId;

    @Column(name = "city_name", nullable = false) // veritabanındaki sütun adını belirtir
    private String cityName;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL) // OneToMany ilişkisi ile 1-N ilişkisi olacağı belirtilir
    // mappedBy = "city" : ile bu ilişkinin kimin tarafından yönetildiği belirtilir.
    // cascade = CascadeType.ALL : ile il seçilinde ilçeler kısmının aktifleşmesi il silinince ilçeler kısmının erişimi de gider.
    private List<District> districts;

    // Getter, Setter, Constructor
    public City() {} // jpa nesneleri oluştururken ilk olarak boş constructor çağrılır sonra parametrelerle constructor çağırır.

    public City(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() { return cityId; }
    public void setCityId(Integer cityId) { this.cityId = cityId; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public List<District> getDistricts() { return districts; }
    public void setDistricts(List<District> districts) { this.districts = districts; }
}
