package entities;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({@NamedQuery(name = "HobbyEntity.deleteAllRows", query = "DELETE from HobbyEntity "),
@NamedQuery(name = "HobbyEntity.getAllHobbies", query = "select h from HobbyEntity h"),

})

public class HobbyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private int id;
    private String name;
    private String description;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "hobbies")
    private List<PersonEntity> persons = new ArrayList<>();

    public HobbyEntity() {
    }

    public HobbyEntity(String name, String description, List<PersonEntity> persons, Date created, Date lastEdited) {
        this.name = name;
        this.description = description;
        this.persons = persons;
    }

    public HobbyEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addPerson(PersonEntity person) {
        this.persons.add(person);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof HobbyEntity)) return false;
        HobbyEntity that = (HobbyEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
