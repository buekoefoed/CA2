package dtos;

import entities.CityInfoEntity;

import java.util.List;

public class CityInfoDTO {
    private String zipCode;
    private String city;
    private List<AddressDTO> addresses;

    public CityInfoDTO(String zipCode, String city, List<AddressDTO> addresses) {
        this.zipCode = zipCode;
        this.city = city;
        this.addresses = addresses;
    }

    public CityInfoDTO(CityInfoEntity cityInfoEntity) {
        this.zipCode = cityInfoEntity.getZipCode();
        this.city = cityInfoEntity.getCity();
        cityInfoEntity.getAddresses().forEach((address) -> this.addresses.add(new AddressDTO(address)));
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

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}
