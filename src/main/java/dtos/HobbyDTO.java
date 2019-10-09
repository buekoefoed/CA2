package dtos;

import entities.HobbyEntity;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

public class HobbyDTO {


    private String name;
    private String description;

    public HobbyDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public HobbyDTO(HobbyEntity hobbyEntity) {
        this.name = hobbyEntity.getName();
        this.description = hobbyEntity.getDescription();
    }

    public HobbyDTO(String name, String description, Date created, Date lastEdited) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HobbyDTO)) return false;
        HobbyDTO hobbyDTO = (HobbyDTO) o;
        return Objects.equals(name, hobbyDTO.name) &&
                Objects.equals(description, hobbyDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
