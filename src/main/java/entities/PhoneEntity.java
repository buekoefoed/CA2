package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "PhoneEntity.deleteAllRows", query = "DELETE from PhoneEntity ")
})
public class PhoneEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    public PhoneEntity() {
    }

    public PhoneEntity(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void addPerson(PersonEntity person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneEntity)) return false;
        PhoneEntity that = (PhoneEntity) o;
        return id == that.id &&
                Objects.equals(number, that.number) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, description);
    }
}
