package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade instance;
    private static PersonEntity person1, person2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        instance = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        person1 = new PersonEntity();
        person1.setEmail("test@test.com");
        person1.setFirstName("Matias");
        person1.setLastName("Koefoed");

        HobbyEntity hobby = new HobbyEntity();
        hobby.setName("Programming");
        hobby.setDescription("Loves suffering");
        person1.addHobby(hobby);

        PhoneEntity phone = new PhoneEntity();
        phone.setNumber("1234");
        phone.setDescription("Home");
        person1.addPhone(phone);

        CityInfoEntity cityInfo = new CityInfoEntity();
        cityInfo.setCity("Rønne");
        cityInfo.setZipCode("3700");

        AddressEntity address = new AddressEntity();
        address.setStreet("Malmøvej");
        address.setAdditionalInfo("17");
        person1.addAddress(address);

        cityInfo.addAddress(address);

        person2 = new PersonEntity();
        person2.setEmail("mail@mail.com");
        person2.setFirstName("Emil");
        person2.setLastName("Hansen");

        HobbyEntity hobby2 = new HobbyEntity();
        hobby2.setName("Vaping phat clouds");
        hobby2.setDescription("dangkk");
        person2.addHobby(hobby2);

        PhoneEntity phone2 = new PhoneEntity();
        phone2.setNumber("4321");
        phone2.setDescription("Office");
        person2.addPhone(phone);

        CityInfoEntity cityInfo2 = new CityInfoEntity();
        cityInfo2.setCity("Gudhjem");
        cityInfo2.setZipCode("3760");

        AddressEntity address2 = new AddressEntity();
        address2.setStreet("Holkavej 3");
        address2.setAdditionalInfo("no");
        person2.addAddress(address);

        cityInfo2.addAddress(address2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("PersonEntity.deleteAllRows").executeUpdate();
            em.persist(person1);
            em.persist(person2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    void getAllPersons() {
        List<PersonEntity> personEntities = instance.getAllPersons();
        assertEquals(2, personEntities.size());
    }

    @Test
    void getPersonByID() {
        PersonEntity personEntity = instance.getPersonByID(person1.getId());
        assertEquals(person1.getFirstName(), personEntity.getFirstName());
    }

    @Test
    void createPerson() {
        PersonDTO personDTO = new PersonDTO("lol@lol.com", "Boris", "Benis");
        PersonEntity personEntity = instance.createPerson(personDTO);
        assertEquals(personDTO.getFirstName(), personEntity.getFirstName());
        assertEquals(3, instance.getAllPersons().size());
    }

    @Test
    void updatePerson() {
        PersonDTO personDTO = new PersonDTO("zumba@lord.com", "Karen", "Fug");
        person2 = instance.updatePerson(person2.getId(), personDTO);
        assertEquals("Karen", person2.getFirstName());
    }

    @Test
    void deletePerson() {
        instance.deletePerson(person1.getId());
        assertEquals(1, instance.getAllPersons().size());
    }
}