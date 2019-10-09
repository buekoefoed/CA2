package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import errorhandling.HobbyNotFoundException;

import java.util.List;

public interface IHobbyFacade {

    List<HobbyDTO> getAllHobbies();

    List<PersonDTO> getAllPersonsByHobby(String hobby);

    String getPersonCountByHobby(String hobby);

    HobbyDTO createHobby(HobbyDTO hobby) throws HobbyNotFoundException;

    HobbyDTO updateHobby(int id, HobbyDTO hobby) throws HobbyNotFoundException;

    HobbyDTO deleteHobby(int id) throws HobbyNotFoundException;

}
