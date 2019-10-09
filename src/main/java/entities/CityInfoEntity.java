package entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "CityInfoEntity.getAllRows",
                query = "SELECT city FROM CityInfoEntity city"),
        @NamedQuery(name = "CityInfoEntity.getAllRowsWhereCity",
                query = "SELECT p FROM PersonEntity p WHERE p.address.cityInfo.city = :city"),
        @NamedQuery(name = "CityInfoEntity.getAllRowsWhereZipCode",
                query = "SELECT p FROM PersonEntity p WHERE p.address.cityInfo.zipCode = :zipCode"),
        @NamedQuery(name = "CityInfoEntity.getAllRowsWhereCount",
                query = "SELECT c FROM AddressEntity a INNER JOIN PersonEntity p INNER JOIN CityInfoEntity c GROUP BY c.id HAVING COUNT(p) > :num"),
        @NamedQuery(name = "CityInfoEntity.deleteAllRows",
                query = "DELETE FROM CityInfoEntity")
})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityInfoEntity)) return false;
        CityInfoEntity that = (CityInfoEntity) o;
        return zipCode.equals(that.zipCode) &&
                city.equals(that.city) &&
                Objects.equals(addresses, that.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, city, addresses);
    }
}
