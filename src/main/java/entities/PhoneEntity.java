package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name = "PhoneEntity.deleteAllRows", query = "DELETE from PhoneEntity ")
public class PhoneEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;
    private String description;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    public PhoneEntity() {
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
}
