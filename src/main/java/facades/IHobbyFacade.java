package facades;

import dtos.HobbyDTO;
import entities.HobbyEntity;
import entities.PersonEntity;

import java.util.List;

public interface IHobbyFacade {

    List<HobbyEntity> getAllHobbies();

    List<PersonEntity> getAllPersonsByHobby(String hobby);

    int getPersonCountByHobby(String hobby);

    HobbyEntity createHobby(HobbyDTO hobby);

    HobbyEntity updateHobby(int id, HobbyDTO hobby);

    HobbyEntity deleteHobby(int id);

}
