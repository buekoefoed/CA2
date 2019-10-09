package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        EntityManager entityManager = getEntityManager();
        try {
            List<PersonDTO> personDTOS = new ArrayList<>();
            List<PersonEntity> personEntities = entityManager.createNamedQuery("PersonEntity.getAllPersons", PersonEntity.class).getResultList();
            personEntities.forEach((personEntity -> personDTOS.add(new PersonDTO(personEntity))));
            return personDTOS;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public PersonDTO getPersonByID(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            PersonEntity personEntity = entityManager.find(PersonEntity.class, id);
            return new PersonDTO(personEntity);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public PersonDTO createPerson(PersonDTO person) {
        EntityManager em = getEntityManager();
        PersonEntity personEntity = new PersonEntity();
        personEntity.setEmail(person.getEmail());
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());

        for (HobbyDTO hobbyDTO : person.getHobbies()) {
            HobbyEntity hobbyEntity = null;
            try {
                hobbyEntity = em.createQuery("select h from HobbyEntity h where h.name = :name", HobbyEntity.class).setParameter("name", hobbyDTO.getName()).getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (hobbyEntity != null) {
                personEntity.addHobby(hobbyEntity);
            } else {
                HobbyEntity hobby = new HobbyEntity();
                hobby.setName(hobbyDTO.getName());
                hobby.setDescription(hobbyDTO.getDescription());
                personEntity.addHobby(hobby);
            }
        }

        for (PhoneDTO phoneDTO : person.getPhones()) {
            PhoneEntity phone = new PhoneEntity();
            phone.setNumber(phoneDTO.getNumber());
            phone.setDescription(phoneDTO.getDescription());
            personEntity.addPhone(phone);
        }

        CityInfoEntity cityInfo = new CityInfoEntity();
        if (person.getCityInfoDTO() != null) {
            try {
                cityInfo = em.createQuery("select c from CityInfoEntity c where c.city = :city and c.zipCode = :zipCode", CityInfoEntity.class)
                        .setParameter("city", person.getCityInfoDTO().getCity())
                        .setParameter("zipCode", person.getCityInfoDTO().getZipCode())
                        .getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cityInfo == null) {
                cityInfo = new CityInfoEntity();
                cityInfo.setCity(person.getCityInfoDTO().getCity());
                cityInfo.setZipCode(person.getCityInfoDTO().getZipCode());
            }
        }

        AddressEntity address = new AddressEntity();
        if (person.getAddress() != null) {
            try {
                address = em.createQuery("select a from AddressEntity a where a.street = :street and a.additionalInfo = :additionalInfo", AddressEntity.class)
                        .setParameter("street", person.getAddress().getStreet())
                        .setParameter("additionalInfo", person.getAddress().getAdditionalInfo())
                        .getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (address == null) {
                address = new AddressEntity();
                address.setStreet(person.getAddress().getStreet());
                address.setAdditionalInfo(person.getAddress().getAdditionalInfo());
            }
        }
        personEntity.addAddress(address);
        cityInfo.addAddress(address);
        try {
            em.getTransaction().begin();
            em.persist(personEntity);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO updatePerson(int id, PersonDTO person) {
        EntityManager em = getEntityManager();
        try {
            PersonEntity personEntity = em.find(PersonEntity.class, id);
            personEntity.setEmail(person.getEmail());
            personEntity.setFirstName(person.getFirstName());
            personEntity.setLastName(person.getLastName());

            for (HobbyDTO hobbyDTO : person.getHobbies()) {
                HobbyEntity hobby = new HobbyEntity();
                hobby.setName(hobbyDTO.getName());
                hobby.setDescription(hobbyDTO.getDescription());
                personEntity.addHobby(hobby);
            }

            for (PhoneDTO phoneDTO : person.getPhones()) {
                PhoneEntity phone = new PhoneEntity();
                phone.setNumber(phoneDTO.getNumber());
                phone.setDescription(phoneDTO.getDescription());
                personEntity.addPhone(phone);
            }

            CityInfoEntity cityInfo = new CityInfoEntity();
            if (person.getCityInfoDTO() != null) {
                cityInfo = new CityInfoEntity();
                cityInfo.setCity(person.getCityInfoDTO().getCity());
                cityInfo.setZipCode(person.getCityInfoDTO().getZipCode());
            }

            if (person.getAddress() != null) {
                AddressEntity address = new AddressEntity();
                address.setStreet(person.getAddress().getStreet());
                address.setAdditionalInfo(person.getAddress().getAdditionalInfo());
                personEntity.addAddress(address);
                cityInfo.addAddress(address);
            }

            em.getTransaction().begin();
            em.merge(personEntity);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonEntity deletePerson(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            PersonEntity personEntity = entityManager.find(PersonEntity.class, id);
            entityManager.remove(personEntity);
            entityManager.getTransaction().commit();
            return personEntity;
        } finally {
            entityManager.close();
        }
    }
}
