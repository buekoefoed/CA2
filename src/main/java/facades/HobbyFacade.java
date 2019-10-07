package facades;

import dtos.HobbyDTO;
import entities.HobbyEntity;
import entities.PersonEntity;
import errorhandling.HobbyNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class HobbyFacade implements IHobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<HobbyEntity> getAllHobbies() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("HobbyEntity.getAllHobbies", HobbyEntity.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonEntity> getAllPersonsByHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT pe from PersonEntity pe where pe.HobbyEntity=:arg1");
            query.setParameter("arg1", hobby);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public int getPersonCountByHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            return (int) em.createQuery("SELECT COUNT(r) FROM HobbyEntity r").getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyEntity createHobby(HobbyDTO hobby) throws HobbyNotFoundException {
        EntityManager em = getEntityManager();
        HobbyEntity newHobby = new HobbyEntity();
        if (newHobby == null) {
            throw new HobbyNotFoundException("Could not create a new bro, something is twisted");
        }
        newHobby.setDescription(hobby.getDescription());
        newHobby.setName(hobby.getName());
        newHobby.setLastEdited();
        try {
            em.getTransaction().begin();
            em.persist(newHobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return newHobby;
    }

    @Override
    public HobbyEntity updateHobby(int id, HobbyDTO hobby) throws HobbyNotFoundException {
        EntityManager em = getEntityManager();
        HobbyEntity hobbyUpdate = em.find(HobbyEntity.class, hobby.getId());
        if (hobbyUpdate == null) {
            throw new HobbyNotFoundException("Could not update bro, something is twisted");
        }
        hobbyUpdate.setId(hobby.getId());
        hobbyUpdate.setName(hobby.getName());
        hobbyUpdate.setDescription(hobby.getDescription());
        hobbyUpdate.setLastEdited();
        try {
            em.getTransaction().begin();
            em.merge(hobbyUpdate);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return hobbyUpdate;

    }

    @Override
    public HobbyEntity deleteHobby(int id) throws HobbyNotFoundException{
        EntityManager em = getEntityManager();
        HobbyEntity hobby = em.find(HobbyEntity.class, id);
        if (hobby == null) {
            throw new HobbyNotFoundException("Could not delete bro, something is twisted");
        }
        try{
            em.getTransaction().begin();
            em.remove(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return hobby;
    }
}
