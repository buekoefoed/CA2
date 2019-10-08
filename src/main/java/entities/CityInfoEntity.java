package entities;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "AddressEntity.deleteAllRows", query = "DELETE from AddressEntity ")
public class CityInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Schema(required = true, example = "3700")
    private String zipCode;
    @Schema(required = true, example = "Rønne")
    private String city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cityInfo")
    private List<AddressEntity> addresses = new ArrayList<>();

    public CityInfoEntity() {
    }

    public void addAddress(AddressEntity address) {
        this.addresses.add(address);
        address.addCityInfo(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<AddressEntity> getAddresses() {
        return addresses;
    }
}
