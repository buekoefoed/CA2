package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;

import java.util.List;

public interface ICityFacade {

    List<CityInfoDTO> getAllCities();

    List<PersonDTO> getPersonsByCity(String city);

    List<PersonDTO> getPersonsByZipCode(String zipCode);

    List<CityInfoDTO> getCitiesByCount(int count);

    CityInfoDTO createCity(CityInfoDTO cityInfo);

    CityInfoDTO updateCity(int id, CityInfoDTO cityInfo);

    CityInfoDTO deleteCity(int id);

}
