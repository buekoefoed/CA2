package facades;

import dtos.PersonDTO;
import entities.PersonEntity;

import java.util.List;

public class PersonFacade implements IPersonFacade {

    @Override
    public List<PersonEntity> getAllPersons() {
        return null;
    }

    @Override
    public PersonEntity getPersonByID(int id) {
        return null;
    }

    @Override
    public PersonEntity createPerson(PersonDTO person) {
        return null;
    }

    @Override
    public PersonEntity updatePerson(int id, PersonDTO person) {
        return null;
    }

    @Override
    public PersonEntity deletePerson(int id) {
        return null;
    }
}
