package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "AddressEntity.deleteAllRows", query = "DELETE from AddressEntity ")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String additionalInfo;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "address")
    private List<PersonEntity> persons = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "CityInfo_id")
    private CityInfoEntity cityInfo;

    public AddressEntity() {
    }

    public void addPerson(PersonEntity person) {
        this.persons.add(person);
    }

    public void addCityInfo(CityInfoEntity cityInfo) {
        this.cityInfo = cityInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public CityInfoEntity getCityInfo() {
        return cityInfo;
    }
}
