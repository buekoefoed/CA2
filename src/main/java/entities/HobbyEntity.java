package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "HobbyEntity.deleteAllRows", query = "DELETE from HobbyEntity "),
@NamedQuery(name = "HobbyEntity.getAllHobbies", query = "select h from HobbyEntity h"),
@NamedQuery(name = "", query = "")

})

public class HobbyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private int id;
    private String name;
    private String description;
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "hobbies")
    private List<PersonEntity> persons = new ArrayList<>();
    @Temporal(TemporalType.DATE)
    private Date created;
    @Temporal(TemporalType.DATE)
    private Date lastEdited;

    public HobbyEntity() {
    }

    public HobbyEntity(String name, String description, List<PersonEntity> persons, Date created, Date lastEdited) {
        this.name = name;
        this.description = description;
        this.persons = persons;
        this.created = created;
        this.lastEdited = lastEdited;
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

    public void setLastEdited() {
        this.lastEdited = new Date();
    }
}
