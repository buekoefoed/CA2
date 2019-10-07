package facades;

import dtos.CityInfoDTO;
import entities.CityInfoEntity;
import entities.PersonEntity;

import java.util.List;

public interface ICityFacade {

    List<CityInfoEntity> getAllCities();

    List<PersonEntity> getPersonsByCity(String city);

    List<PersonEntity> getPersonsByZipCode(String zipCode);

    List<CityInfoEntity> getCitiesByCount(int count);

    CityInfoEntity createCity(CityInfoDTO cityInfo);

    CityInfoEntity updateCity(int id, CityInfoDTO cityInfo);

    CityInfoEntity deleteCity(int id);

}
