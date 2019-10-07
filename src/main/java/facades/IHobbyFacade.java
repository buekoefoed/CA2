package facades;

import dtos.HobbyDTO;
import entities.HobbyEntity;
import entities.PersonEntity;
import errorhandling.HobbyNotFoundException;

import java.util.List;

public interface IHobbyFacade {

    List<HobbyEntity> getAllHobbies();

    List<PersonEntity> getAllPersonsByHobby(String hobby);

    int getPersonCountByHobby(String hobby);

    HobbyEntity createHobby(HobbyDTO hobby) throws HobbyNotFoundException;

    HobbyEntity updateHobby(int id, HobbyDTO hobby) throws HobbyNotFoundException;

    HobbyEntity deleteHobby(int id) throws HobbyNotFoundException;

}
