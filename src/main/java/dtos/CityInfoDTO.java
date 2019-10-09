package dtos;

import entities.CityInfoEntity;

import java.util.List;
import java.util.Objects;

public class CityInfoDTO {
    private String zipCode;
    private String city;

    public CityInfoDTO(String zipCode, String city, List<AddressDTO> addresses) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfoDTO(CityInfoEntity cityInfoEntity) {
        this.zipCode = cityInfoEntity.getZipCode();
        this.city = cityInfoEntity.getCity();
    }

    public CityInfoDTO() {
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CityInfoDTO{" +
                "zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityInfoDTO)) return false;
        CityInfoDTO that = (CityInfoDTO) o;
        return Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, city);
    }
}
