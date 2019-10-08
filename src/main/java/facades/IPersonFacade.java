package facades;

import dtos.PersonDTO;
import entities.PersonEntity;

import java.util.List;

public interface IPersonFacade {

    List<PersonDTO> getAllPersons();

    PersonDTO getPersonByID(int id);

    PersonDTO createPerson(PersonDTO person);

    PersonDTO updatePerson(int id, PersonDTO person);

    PersonEntity deletePerson(int id);

}
