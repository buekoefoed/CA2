package dtos;

import entities.HobbyEntity;
import entities.PersonEntity;
import entities.PhoneEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    @Schema(required = true, example = "email@email.com")
    private String email;
    private String firstName;
    private String lastName;
    private List<PhoneDTO> phones = new ArrayList<>();
    private AddressDTO address;
    private CityInfoDTO cityInfoDTO;
    private List<HobbyDTO> hobbyDTOS = new ArrayList<>();

    @Override
    public String toString() {
        return "PersonDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phones=" + phones.toString() +
                ", address=" + address.toString() +
                ", cityInfoDTO=" + cityInfoDTO.toString() +
                ", hobbies=" + hobbyDTOS.toString() +
                '}';
    }



    public PersonDTO(String email, String firstName, String lastName, List<PhoneDTO> phones, AddressDTO address, List<HobbyDTO> hobbies) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
        this.address = address;
        this.hobbyDTOS = hobbies;
    }

    public PersonDTO(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new ArrayList<>();
        this.address = new AddressDTO();
        this.hobbyDTOS = new ArrayList<>();
    }

    public PersonDTO() {
    }

    public PersonDTO(PersonEntity personEntity) {
        this.email = personEntity.getEmail();
        this.firstName = personEntity.getFirstName();
        this.lastName = personEntity.getLastName();
        for (PhoneEntity phoneEntity : personEntity.getPhones()) {
            this.phones.add(new PhoneDTO(phoneEntity));
        }
        this.address = new AddressDTO(personEntity.getAddress());
        this.cityInfoDTO = new CityInfoDTO(personEntity.getAddress().getCityInfo());
        for (HobbyEntity hobbyEntity : personEntity.getHobbies()) {
                this.hobbyDTOS.add(new HobbyDTO(hobbyEntity));
        }
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
        return hobbyDTOS;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbyDTOS = hobbies;
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }
}
