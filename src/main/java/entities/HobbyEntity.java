package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "HobbyEntity.deleteAllRows", query = "DELETE from HobbyEntity ")
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
}
