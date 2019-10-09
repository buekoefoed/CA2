package dtos;

import entities.AddressEntity;

public class AddressDTO {
    private String street;
    private String additionalInfo;

    public AddressDTO(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }

    public AddressDTO(AddressEntity addressEntity) {
        this.street = addressEntity.getStreet();
        this.additionalInfo = addressEntity.getAdditionalInfo();
    }

    public AddressDTO() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "street='" + street + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
