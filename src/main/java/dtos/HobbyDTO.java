package dtos;

import entities.HobbyEntity;

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
}
