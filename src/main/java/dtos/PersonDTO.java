package dtos;

import entities.PersonEntity;
import entities.PhoneEntity;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private String email;
    private String firstName;
    private String lastName;
    private List<PhoneDTO> phones;
    private AddressDTO address;
    private CityInfoDTO cityInfoDTO;
    private List<HobbyDTO> hobbies;

    public PersonDTO(String email, String firstName, String lastName, List<PhoneDTO> phones, AddressDTO address, List<HobbyDTO> hobbies) {
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
        this.address = new AddressDTO();
        this.hobbies = new ArrayList<>();
    }

    public PersonDTO() {
    }

    public PersonDTO(PersonEntity personEntity) {
        this.email = personEntity.getEmail();
        this.firstName = personEntity.getFirstName();
        this.lastName = personEntity.getLastName();
        personEntity.getPhones().forEach(phoneEntity -> this.phones.add(new PhoneDTO(phoneEntity)));
        this.address = new AddressDTO(personEntity.getAddress());
        this.cityInfoDTO = new CityInfoDTO(personEntity.getAddress().getCityInfo());
        personEntity.getHobbies().forEach(hobbyEntity -> this.hobbies.add(new HobbyDTO(hobbyEntity)));
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }
}
