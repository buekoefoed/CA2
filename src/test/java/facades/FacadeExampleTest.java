package facades;

import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static FacadeExample facade;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = FacadeExample.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        PersonEntity person = new PersonEntity();
        person.setEmail("test@test.com");
        person.setFirstName("Matias");
        person.setLastName("Koefoed");

        HobbyEntity hobby = new HobbyEntity();
        hobby.setName("Programming");
        hobby.setDescription("Loves suffering");
        person.addHobby(hobby);

        PhoneEntity phone = new PhoneEntity();
        phone.setNumber("1234");
        phone.setDescription("Home");
        person.addPhone(phone);

        CityInfoEntity cityInfo = new CityInfoEntity();
        cityInfo.setCity("Rønne");
        cityInfo.setZipCode("3700");

        AddressEntity address = new AddressEntity();
        address.setStreet("Malmøvej");
        address.setAdditionalInfo("17");
        person.addAddress(address);

        cityInfo.addAddress(address);

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

}
