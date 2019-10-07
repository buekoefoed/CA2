package facades;

import dtos.HobbyDTO;
import entities.HobbyEntity;
import entities.PersonEntity;

import java.util.List;

public class HobbyFacade implements IHobbyFacade {

    @Override
    public List<HobbyEntity> getAllHobbies() {
        return null;
    }

    @Override
    public List<PersonEntity> getAllPersonsByHobby(String hobby) {
        return null;
    }

    @Override
    public int getPersonCountByHobby(String hobby) {
        return 0;
    }

    @Override
    public HobbyEntity createHobby(HobbyDTO hobby) {
        return null;
    }

    @Override
    public HobbyEntity updateHobby(int id, HobbyDTO hobby) {
        return null;
    }

    @Override
    public HobbyEntity deleteHobby(int id) {
        return null;
    }
}
