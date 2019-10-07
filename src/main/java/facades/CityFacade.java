package facades;

import dtos.CityInfoDTO;
import entities.CityInfoEntity;
import entities.PersonEntity;

import java.util.List;

public class CityFacade implements ICityFacade {

    @Override
    public List<CityInfoEntity> getAllCities() {
        return null;
    }

    @Override
    public List<PersonEntity> getPersonsByCity(String city) {
        return null;
    }

    @Override
    public List<PersonEntity> getPersonsByZipCode(String zipCode) {
        return null;
    }

    @Override
    public List<CityInfoEntity> getCitiesByCount(int count) {
        return null;
    }

    @Override
    public CityInfoEntity createCity(CityInfoDTO cityInfo) {
        return null;
    }

    @Override
    public CityInfoEntity updateCity(int id, CityInfoDTO cityInfo) {
        return null;
    }

    @Override
    public CityInfoEntity deleteCity(int id) {
        return null;
    }
}
