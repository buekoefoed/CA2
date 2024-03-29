package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.HobbyEntity;
import entities.PersonEntity;
import errorhandling.HobbyNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
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
    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = getEntityManager();
        try {
            List<HobbyDTO> hobbyDTOS = new ArrayList<>();
            List<HobbyEntity> hobbyEntities = em.createNamedQuery("HobbyEntity.getAllHobbies", HobbyEntity.class).getResultList();
            hobbyEntities.forEach((hobbyEntities2 -> hobbyDTOS.add(new HobbyDTO(hobbyEntities2))));
            return hobbyDTOS;
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getAllPersonsByHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            List<PersonDTO> personDTOS = new ArrayList<>();
            List<PersonEntity> personEntities = em.createQuery("SELECT p from PersonEntity p join p.hobbies h where h.name = :name", PersonEntity.class)
            .setParameter("name", hobby)
            .getResultList();
            personEntities.forEach(personEntity -> personDTOS.add(new PersonDTO(personEntity)));
            return personDTOS;
        } finally {
            em.close();
        }
    }

    @Override
    public String getPersonCountByHobby(String hobby) {
        EntityManager em = getEntityManager();
        try {
            return String.valueOf(em.createQuery("SELECT COUNT(p) FROM HobbyEntity h join h.persons p where h.name = :name")
                    .setParameter("name",hobby)
                    .getSingleResult());
        } finally {
            em.close();
        }
    }

    @Override
    public HobbyDTO createHobby(HobbyDTO hobby) {
        EntityManager em = getEntityManager();
        HobbyEntity newHobby = new HobbyEntity();
        newHobby.setDescription(hobby.getDescription());
        newHobby.setName(hobby.getName());
        try {
            em.getTransaction().begin();
            em.persist(newHobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return hobby;

    }

    @Override
    public HobbyDTO updateHobby(int id, HobbyDTO hobby) throws HobbyNotFoundException {
        EntityManager em = getEntityManager();
        HobbyEntity hobbyUpdate = em.find(HobbyEntity.class, id);
        if (hobbyUpdate == null) {
            throw new HobbyNotFoundException("Could not update bro, something is twisted");
        }
        hobbyUpdate.setName(hobby.getName());
        hobbyUpdate.setDescription(hobby.getDescription());
        try {
            em.getTransaction().begin();
            em.merge(hobbyUpdate);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobbyUpdate);
    }

    @Override
    public HobbyDTO deleteHobby(int id) throws HobbyNotFoundException{
        EntityManager em = getEntityManager();
        HobbyEntity hobby = em.find(HobbyEntity.class, id);
        if (hobby == null) {
            throw new HobbyNotFoundException("Could not delete bro, something is twisted");
        }
        try{
            em.getTransaction().begin();
            em.remove(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }
}
