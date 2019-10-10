package facades;

import dtos.AddressDTO;
import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.*;
import errorhandling.CityInfoEntityNotFoundException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
class CityFacadeTest {

    private static EntityManagerFactory emf;
    private static CityFacade facade;
    private static PersonEntity p1, p2, p3;
    private static CityInfoEntity c1, c2, c3;
    private static PersonDTO p1DTO, p2DTO, p3DTO;
    private static CityInfoDTO c1DTO, c2DTO, c3DTO;

    public CityFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = CityFacade.getCityFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        // <editor-fold defaultstate="collapsed" desc="Test data: p1, p2, p3. Click on the + sign to expand.">
        p1 = new PersonEntity();
        p2 = new PersonEntity();
        p3 = new PersonEntity();

        p1.setEmail("matias@test.com");
        p1.setFirstName("Matias");
        p1.setLastName("Koefoed");

        p2.setEmail("karl@test.com");
        p2.setFirstName("Karl");
        p2.setLastName("Frödin");

        p3.setEmail("emil@test.com");
        p3.setFirstName("Emil");
        p3.setLastName("Hansen");

        HobbyEntity h1 = new HobbyEntity();
        h1.setName("CityFacade");
        h1.setDescription("Makes city facades");
        p1.addHobby(h1);

        HobbyEntity h2 = new HobbyEntity();
        h2.setName("HobbyFacade");
        h2.setDescription("Makes hobby facades");
        p2.addHobby(h2);

        HobbyEntity h3 = new HobbyEntity();
        h3.setName("PersonFacade");
        h3.setDescription("Makes person facades");
        p3.addHobby(h3);

        PhoneEntity ph1 = new PhoneEntity();
        ph1.setNumber("1111");
        ph1.setDescription("Home");
        p1.addPhone(ph1);

        PhoneEntity ph2 = new PhoneEntity();
        ph2.setNumber("2222");
        ph2.setDescription("Home");
        p2.addPhone(ph2);

        PhoneEntity ph3 = new PhoneEntity();
        ph3.setNumber("3333");
        ph3.setDescription("Home");
        p3.addPhone(ph3);

        c1 = new CityInfoEntity();
        c1.setCity("Rønne");
        c1.setZipCode("3700");

        c2 = new CityInfoEntity();
        c2.setCity("Allinge");
        c2.setZipCode("3770");

        c3 = new CityInfoEntity();
        c3.setCity("Gudhjem");
        c3.setZipCode("3760");

        AddressEntity a1 = new AddressEntity();
        a1.setStreet("Malmøvej");
        a1.setAdditionalInfo("17");
        p1.addAddress(a1);

        AddressEntity a2 = new AddressEntity();
        a2.setStreet("Ågade");
        a2.setAdditionalInfo("6");
        p2.addAddress(a2);

        AddressEntity a3 = new AddressEntity();
        a3.setStreet("Holkavej");
        a3.setAdditionalInfo("3");
        p3.addAddress(a3);

        c1.addAddress(a1);
        c2.addAddress(a2);
        c3.addAddress(a3);

        p1DTO = new PersonDTO(p1);
        p2DTO = new PersonDTO(p2);
        p3DTO = new PersonDTO(p3);

        c1DTO = new CityInfoDTO(c1);
        c2DTO = new CityInfoDTO(c2);
        c3DTO = new CityInfoDTO(c3);
        // </editor-fold>

        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("select p from PersonEntity p", PersonEntity.class).getResultList().forEach((em::remove));
            em.createQuery("select c from CityInfoEntity c", CityInfoEntity.class).getResultList().forEach((em::remove));
            em.createQuery("select h from HobbyEntity h", HobbyEntity.class).getResultList().forEach(em::remove);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getAllCities() {
        List<CityInfoDTO> cities = facade.getAllCities();
        assertThat(cities, containsInAnyOrder(c1DTO, c2DTO, c3DTO));
        assertEquals(3, cities.size());
    }

    @Test
    void getPersonsByCity() {
        List<PersonDTO> persons = facade.getPersonsByCity("Rønne");
        assertEquals(p1DTO, persons.get(0));
    }

    @Test
    void getPersonsByZipCode() {
        List<PersonDTO> persons = facade.getPersonsByZipCode("3770");
        assertEquals(p2DTO, persons.get(0));
    }

    @Test
    void getCitiesByCount() {
        List<CityInfoDTO> cities = facade.getCitiesByCount(0);
        assertEquals(3, cities.size());
    }

    @Test
    void createCity() {
        CityInfoDTO cityDTO = new CityInfoDTO();
        cityDTO.setZipCode("2500");
        cityDTO.setCity("Valby");

        CityInfoDTO city = facade.createCity(cityDTO);
        List<CityInfoDTO> cities = facade.getAllCities();
        assertThat(cities, containsInAnyOrder(c1DTO, c2DTO, c3DTO, city));
    }

    @Test
    void updateCity() throws CityInfoEntityNotFoundException {
        EntityManager em = emf.createEntityManager();

        CityInfoDTO cityDTO = new CityInfoDTO();
        cityDTO.setZipCode("2000");
        cityDTO.setCity("Frederiksberg");

        facade.updateCity(c1.getId(), cityDTO);

        CityInfoEntity city = em.find(CityInfoEntity.class, c1.getId());

        assertEquals("2000", city.getZipCode());
        assertEquals("Frederiksberg", city.getCity());
    }

    @Test
    void deleteCity() {
        /*
        facade.deleteCity(c1.getId());
        List<CityInfoDTO> cities = facade.getAllCities();
        assertEquals(2, cities.size());
        assertThat(cities, containsInAnyOrder(c2DTO, c3DTO));
        assertThat(cities, not(contains(c1DTO)));
        */
    }
}