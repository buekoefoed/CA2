package facades;

import dtos.*;
import entities.*;
import errorhandling.*;

import javax.persistence.*;
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
            HobbyEntity hobbyEntity = new HobbyEntity();
            try {
                hobbyEntity = em.createQuery("select h from HobbyEntity h where h.name = :name", HobbyEntity.class).setParameter("name", hobbyDTO.getName()).getSingleResult();
            } catch (Exception e) {
                hobbyEntity.setName(hobbyDTO.getName());
                hobbyEntity.setDescription(hobbyDTO.getDescription());
            }
            personEntity.addHobby(hobbyEntity);
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
                cityInfo.setCity(person.getCityInfoDTO().getCity());
                cityInfo.setZipCode(person.getCityInfoDTO().getZipCode());
            }
        }

        AddressEntity address;
        if (person.getAddress() != null) {
            try {
                address = em.createQuery("select a from AddressEntity a where a.street = :street and a.additionalInfo = :additionalInfo", AddressEntity.class)
                        .setParameter("street", person.getAddress().getStreet())
                        .setParameter("additionalInfo", person.getAddress().getAdditionalInfo())
                        .getSingleResult();
            } catch (Exception e) {
                address = new AddressEntity();
                address.setStreet(person.getAddress().getStreet());
                address.setAdditionalInfo(person.getAddress().getAdditionalInfo());
            }
            personEntity.addAddress(address);
            cityInfo.addAddress(address);
        }
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
    public PersonDTO updatePerson(int id, PersonDTO person) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        PersonEntity personEntity = em.find(PersonEntity.class, id);
        if (personEntity == null) {
            throw new PersonNotFoundException("Person not found");
        }
        if (person.getEmail() != null) {
            personEntity.setEmail(person.getEmail());
        }
        if (person.getFirstName() != null) {
            personEntity.setFirstName(person.getFirstName());
        }
        if (person.getLastName() != null) {
            personEntity.setLastName(person.getLastName());
        }
        if (person.getPhones() != null) {
            for (PhoneDTO phone : person.getPhones()) {
                PhoneEntity phoneEntity;
                try {
                    phoneEntity = findPhone(phone);
                    phoneEntity.setDescription(phone.getDescription());
                    phoneEntity = em.merge(phoneEntity);
                } catch (PhoneNotFoundException e) {
                    phoneEntity = new PhoneEntity(phone.getNumber(), phone.getDescription());
                    em.persist(phoneEntity);
                }
                if (!personEntity.getPhones().contains(phoneEntity)) {
                    personEntity.addPhone(phoneEntity);
                }
            }
        }
        CityInfoEntity cityInfoEntity = new CityInfoEntity();
        if (person.getCityInfoDTO() != null) {
            try {
                cityInfoEntity = findCityInfoEntity(person.getCityInfoDTO());
                cityInfoEntity = em.merge(cityInfoEntity);
            } catch (CityInfoEntityNotFoundException e) {
                cityInfoEntity = new CityInfoEntity(person.getCityInfoDTO().getCity(), person.getCityInfoDTO().getZipCode());
                em.persist(cityInfoEntity);
            }
        }
        AddressEntity addressEntity = new AddressEntity();
        if (person.getAddress() != null) {
            try {
                addressEntity = findAddressEntity(person.getAddress());
                addressEntity = em.merge(addressEntity);
            } catch (AddressNotFoundException e) {
                addressEntity = new AddressEntity(person.getAddress().getStreet(), person.getAddress().getAdditionalInfo());
                em.persist(addressEntity);
            }
        }
        personEntity.setAddress(addressEntity);
        cityInfoEntity.addAddress(addressEntity);
        if (person.getHobbies() != null) {
            for (HobbyDTO hobbyDTO : person.getHobbies()) {
                HobbyEntity hobbyEntity;
                try {
                    hobbyEntity = findHobbyEntity(hobbyDTO);
                    hobbyEntity.setDescription(hobbyDTO.getDescription());
                    hobbyEntity = em.merge(hobbyEntity);
                } catch (HobbyNotFoundException e) {
                    hobbyEntity = new HobbyEntity(hobbyDTO.getName(), hobbyDTO.getDescription());
                    em.persist(hobbyEntity);
                }
                if (!personEntity.getHobbies().contains(hobbyEntity)) {
                    personEntity.addHobby(hobbyEntity);
                }
            }
        }
        PersonEntity mergedPerson = em.merge(personEntity);
        em.getTransaction().commit();
        System.out.println("sup bitch");
        em.close();
        return new PersonDTO(mergedPerson);
    }

    private HobbyEntity findHobbyEntity(HobbyDTO hobbyDTO) throws HobbyNotFoundException {
        EntityManager entityManager = getEntityManager();
        HobbyEntity hobbyEntity;
        TypedQuery<HobbyEntity> query = entityManager.createQuery("select h from HobbyEntity h where h.name = :name", HobbyEntity.class)
                .setParameter("name", hobbyDTO.getName());
        try {
            hobbyEntity = query.getSingleResult();
        } catch (NonUniqueResultException e) {
            return query.getResultList().get(1);
        } catch (NoResultException e) {
            throw new HobbyNotFoundException("Hobby not found");
        }
        return hobbyEntity;
    }

    private AddressEntity findAddressEntity(AddressDTO address) throws AddressNotFoundException {
        EntityManager entityManager = getEntityManager();
        AddressEntity addressEntity;
        TypedQuery<AddressEntity> query = entityManager.createQuery("select a from AddressEntity a where a.street = :street and a.additionalInfo = :info", AddressEntity.class)
                .setParameter("street", address.getStreet())
                .setParameter("info", address.getAdditionalInfo());
        try {
            addressEntity = query.getSingleResult();
        } catch (NonUniqueResultException e) {
            return query.getResultList().get(1);
        } catch (NoResultException e) {
            throw new AddressNotFoundException("No address found");
        }
        return addressEntity;
    }

    private CityInfoEntity findCityInfoEntity(CityInfoDTO cityInfoDTO) throws CityInfoEntityNotFoundException {
        EntityManager entityManager = getEntityManager();
        CityInfoEntity cityInfoEntity;
        TypedQuery<CityInfoEntity> query = entityManager.createQuery("select c from CityInfoEntity c where c.city = :city and c.zipCode = :zip", CityInfoEntity.class)
                .setParameter("city", cityInfoDTO.getCity())
                .setParameter("zip", cityInfoDTO.getZipCode());
        try {
            cityInfoEntity = query.getSingleResult();
        } catch (NonUniqueResultException e) {
            return query.getResultList().get(1);
        } catch (NoResultException e) {
            throw new CityInfoEntityNotFoundException("No city info found");
        }
        return cityInfoEntity;
    }

    private PhoneEntity findPhone(PhoneDTO phoneDTO) throws PhoneNotFoundException {
        EntityManager entityManager = getEntityManager();
        PhoneEntity phoneEntity;
        TypedQuery<PhoneEntity> query = entityManager.createQuery("select p from PhoneEntity p where p.number = :num", PhoneEntity.class)
                .setParameter("num", phoneDTO.getNumber());
        try {
            phoneEntity = query.getSingleResult();
        } catch (NonUniqueResultException e) {
            return query.getResultList().get(1);
        } catch (NoResultException e) {
            throw new PhoneNotFoundException("No address found");
        }
        return phoneEntity;
    }

    @Override
    public PersonDTO deletePerson(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            PersonEntity personEntity = entityManager.find(PersonEntity.class, id);
            entityManager.remove(personEntity);
            entityManager.getTransaction().commit();
            return new PersonDTO(personEntity);
        } finally {
            entityManager.close();
        }
    }
}
