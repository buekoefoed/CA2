package dtos;

import entities.PhoneEntity;

import java.util.Objects;

public class PhoneDTO {
    private String number;
    private String description;

    public PhoneDTO(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public PhoneDTO(PhoneEntity phoneEntity) {
        this.number = phoneEntity.getNumber();
        this.description = phoneEntity.getDescription();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "number='" + number + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneDTO)) return false;
        PhoneDTO phoneDTO = (PhoneDTO) o;
        return Objects.equals(number, phoneDTO.number) &&
                Objects.equals(description, phoneDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, description);
    }
}
