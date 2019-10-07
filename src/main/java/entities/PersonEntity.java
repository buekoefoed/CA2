package entities;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "PersonEntity.deleteAllRows", query = "DELETE from PersonEntity ")
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "person")
    @CascadeOnDelete
    private List<PhoneEntity> phones = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_hobby_LINKTABLE",
    joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "hobby_id", referencedColumnName = "hobby_id"))
    private List<HobbyEntity> hobbies = new ArrayList<>();


    public PersonEntity() {
    }

    public void addPhone(PhoneEntity phone) {
        this.phones.add(phone);
        phone.addPerson(this);
    }

    public void addAddress(AddressEntity address) {
        this.address = address;
        address.addPerson(this);
    }

    public void addHobby(HobbyEntity hobby) {
        this.hobbies.add(hobby);
        hobby.addPerson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
