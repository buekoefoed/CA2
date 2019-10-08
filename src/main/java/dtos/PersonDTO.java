package dtos;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private String email;
    private String firstName;
    private String lastName;
    private List<PhoneDTO> phones;
    private CityInfoDTO address;
    private List<HobbyDTO> hobbies;

    public PersonDTO(String email, String firstName, String lastName, List<PhoneDTO> phones, CityInfoDTO address, List<HobbyDTO> hobbies) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
        this.address = address;
        this.hobbies = hobbies;
    }

    public PersonDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new ArrayList<>();
        this.address = new CityInfoDTO();
        this.hobbies = new ArrayList<>();
    }

    public PersonDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public CityInfoDTO getAddress() {
        return address;
    }

    public void setAddress(CityInfoDTO address) {
        this.address = address;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }
}
