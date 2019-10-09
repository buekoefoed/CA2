package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfoEntity;
import entities.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class CityFacade implements ICityFacade {

    private static CityFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityFacade getCityFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<CityInfoDTO> getAllCities() {
        EntityManager em = getEntityManager();
        try {
            List<CityInfoDTO> citiesDTO = new ArrayList<>();
            List<CityInfoEntity> cities = em.createNamedQuery("CityInfoEntity.getAllRows", CityInfoEntity.class).getResultList();
            cities.forEach(city -> citiesDTO.add(new CityInfoDTO(city)));
            return citiesDTO;
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonsByCity(String city) {
        EntityManager em = getEntityManager();
        try {
            List<PersonDTO> personsDTO = new ArrayList<>();
            List<PersonEntity> persons = em.createNamedQuery("CityInfoEntity.getAllRowsWhereCity", PersonEntity.class)
                    .setParameter("city", city).getResultList();
            persons.forEach(person -> personsDTO.add(new PersonDTO(person)));
            return personsDTO;
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonsByZipCode(String zipCode) {
        EntityManager em = getEntityManager();
        try {
            List<PersonDTO> personsDTO = new ArrayList<>();
            List<PersonEntity> persons = em.createNamedQuery("CityInfoEntity.getAllRowsWhereZipCode", PersonEntity.class)
                    .setParameter("zipCode", zipCode).getResultList();
            persons.forEach(person -> personsDTO.add(new PersonDTO(person)));
            return personsDTO;
        } finally {
            em.close();
        }
    }

    @Override
    public List<CityInfoDTO> getCitiesByCount(int num) {
        EntityManager em = getEntityManager();
        try {
            List<CityInfoDTO> citiesDTO = new ArrayList<>();
            List<CityInfoEntity> cities = em.createNamedQuery("CityInfoEntity.getAllRowsWhereCount", CityInfoEntity.class)
                    .setParameter("num", num).getResultList();
            cities.forEach(city -> citiesDTO.add(new CityInfoDTO(city)));
            return citiesDTO;
        } finally {
            em.close();
        }
    }

    @Override
    public CityInfoDTO createCity(CityInfoDTO cityInfo) {
        EntityManager em = getEntityManager();

        CityInfoEntity city = new CityInfoEntity();
        city.setZipCode(cityInfo.getZipCode());
        city.setCity(cityInfo.getCity());

        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();

        return new CityInfoDTO(city);
    }

    @Override
    public CityInfoDTO updateCity(int id, CityInfoDTO cityInfo) {
        EntityManager em = getEntityManager();
        CityInfoEntity city = em.find(CityInfoEntity.class, id);

        if (cityInfo.getZipCode() != null) {
            city.setZipCode(cityInfo.getZipCode());
        }

        if (cityInfo.getCity() != null) {
            city.setCity(cityInfo.getCity());
        }

        try {
            em.getTransaction().begin();
            em.merge(city);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new CityInfoDTO(city);
    }

    @Override
    public CityInfoDTO deleteCity(int id) {
        EntityManager em = getEntityManager();
        CityInfoEntity city = em.find(CityInfoEntity.class, id);

        if (city != null) {
            try {
                em.getTransaction().begin();
                em.remove(city);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new CityInfoDTO(city);
    }
}
