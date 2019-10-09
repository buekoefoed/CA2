package facades;

import dtos.PersonDTO;
import errorhandling.PersonNotFoundException;

import java.util.List;

public interface IPersonFacade {

    List<PersonDTO> getAllPersons();

    PersonDTO getPersonByID(int id);

    PersonDTO createPerson(PersonDTO person);

    PersonDTO updatePerson(int id, PersonDTO person) throws PersonNotFoundException;

    PersonDTO deletePerson(int id);

}
