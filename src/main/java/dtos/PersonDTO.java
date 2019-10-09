package dtos;

import entities.HobbyEntity;
import entities.PersonEntity;
import entities.PhoneEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDTO {
    @Schema(required = true, example = "email@email.com")
    private String email;
    @Schema(required = true, example = "Lars")
    private String firstName;
    @Schema(required = true, example = "Larsen")
    private String lastName;
    @Schema(example = " [\n" +
            "    {\n" +
            "      \"number\": \"1231421\",\n" +
            "      \"description\": \"office\"\n" +
            "    }\n" +
            "  ]")
    private List<PhoneDTO> phones = new ArrayList<>();
    @Schema(example = "{\n" +
            "    \"street\": \"Holkavej 3\",\n" +
            "    \"additionalInfo\": \"Hvad du nu vil\"\n" +
            "  }")
    private AddressDTO address;
    @Schema(example = "{\n" +
            "    \"zipCode\": \"3760\",\n" +
            "    \"city\": \"Gudhjem\"\n" +
            "  }")
    private CityInfoDTO cityInfoDTO;
    @Schema(example = "[\n" +
            "    {\n" +
            "      \"name\": \"Fodbold\",\n" +
            "      \"description\": \"Fod til bold\",\n" +
            "    }\n" +
            "  ]")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(email, personDTO.email) &&
                Objects.equals(firstName, personDTO.firstName) &&
                Objects.equals(lastName, personDTO.lastName) &&
                Objects.equals(phones, personDTO.phones) &&
                Objects.equals(address, personDTO.address) &&
                Objects.equals(cityInfoDTO, personDTO.cityInfoDTO) &&
                Objects.equals(hobbyDTOS, personDTO.hobbyDTOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, phones, address, cityInfoDTO, hobbyDTOS);
    }
}
