package facades;

import dtos.PersonDTO;
import entities.PersonEntity;

import java.util.List;

public interface IPersonFacade {

    List<PersonEntity> getAllPersons();

    PersonEntity getPersonByID(int id);

    PersonEntity createPerson(PersonDTO person);

    PersonEntity updatePerson(int id, PersonDTO person);

    PersonEntity deletePerson(int id);

}
