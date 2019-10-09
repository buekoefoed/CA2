package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
import entities.AddressEntity;
import entities.CityInfoEntity;
import entities.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public List<CityInfoEntity> getAllCities() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("CityInfoEntity.getAllRows", CityInfoEntity.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonEntity> getPersonsByCity(String city) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("CityInfoEntity.getAllRowsWhereCity", PersonEntity.class)
                    .setParameter("city", city).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonEntity> getPersonsByZipCode(String zipCode) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("CityInfoEntity.getAllRowsWhereZipCode", PersonEntity.class)
                    .setParameter("zipCode", zipCode).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<CityInfoEntity> getCitiesByCount(int num) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("CityInfoEntity.getAllRowsWhereCount", CityInfoEntity.class)
                    .setParameter("num", num).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CityInfoEntity createCity(CityInfoDTO cityInfo) {
        EntityManager em = getEntityManager();

        CityInfoEntity city = new CityInfoEntity();
        city.setZipCode(cityInfo.getZipCode());
        city.setCity(cityInfo.getCity());

        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();

        return city;
    }

    @Override
    public CityInfoEntity updateCity(int id, CityInfoDTO cityInfo) {
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

        return city;
    }

    @Override
    public CityInfoEntity deleteCity(int id) {
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

        return city;
    }
}
