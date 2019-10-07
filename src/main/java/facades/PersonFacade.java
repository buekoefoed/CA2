package facades;

import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public List<PersonEntity> getAllPersons() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createNamedQuery("PersonEntity.getAllPersons", PersonEntity.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public PersonEntity getPersonByID(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(PersonEntity.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public PersonEntity createPerson(PersonDTO person) {
        EntityManager em = getEntityManager();
        PersonEntity personEntity = new PersonEntity();
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
        cityInfo.setCity(person.getAddress().getCity());
        cityInfo.setZipCode(person.getAddress().getZipCode());

        if (person.getAddress().getAddresses() != null) {
            for (AddressDTO addressDTO: person.getAddress().getAddresses()) {
                AddressEntity address = new AddressEntity();
                address.setStreet(addressDTO.getStreet());
                address.setAdditionalInfo(addressDTO.getAdditionalInfo());
                personEntity.addAddress(address);
                cityInfo.addAddress(address);
            }
        }

        try {
            em.getTransaction().begin();
            em.persist(personEntity);
            em.getTransaction().commit();
            return personEntity;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonEntity updatePerson(int id, PersonDTO person) {
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
            cityInfo.setCity(person.getAddress().getCity());
            cityInfo.setZipCode(person.getAddress().getZipCode());

            if (person.getAddress().getAddresses() != null) {
                for (AddressDTO addressDTO: person.getAddress().getAddresses()) {
                    AddressEntity address = new AddressEntity();
                    address.setStreet(addressDTO.getStreet());
                    address.setAdditionalInfo(addressDTO.getAdditionalInfo());
                    personEntity.addAddress(address);
                    cityInfo.addAddress(address);
                }
            }

            em.getTransaction().begin();
            em.merge(personEntity);
            em.getTransaction().commit();
            return personEntity;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonEntity deletePerson(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            PersonEntity personEntity = entityManager.find(PersonEntity.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(personEntity);
            entityManager.getTransaction().commit();
            return personEntity;
        } finally {
            entityManager.close();
        }
    }
}
