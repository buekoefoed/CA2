package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.HobbyEntity;
import errorhandling.HobbyNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Disabled
class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    private static HobbyEntity h1, h2, h3;


    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
//        emf = EMF_Creator.createEntityManagerFactory(
//                "pu",
//                "jdbc:mysql://localhost:3307/ca2_test",
//                "dev",
//                "ax2",
//                EMF_Creator.Strategy.DROP_AND_CREATE);
        //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    /*   **** HINT ****
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that.
        See below for how to use these files. This is our RECOMENDED strategy
    */


    @AfterAll
    public static void tearDownClass() {
        // Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        h1 = new HobbyEntity("Procrastination", "I will write this later");
        h2 = new HobbyEntity ("Jump rope","Swing a string over your head");
        h3 = new HobbyEntity("Boxing","Try not to get hit");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("HobbyEntity.deleteAllRows").executeUpdate();
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        // Remove any data after each test was run
    }

//    @Test
//    void getAllHobbies() {
//        List<HobbyDTO> hobbyEntityList = facade.getAllHobbies();
//        assertEquals(3, hobbyEntityList.size(),
//                "Assert that you get all hobbies");
//    }

//    @Test
//    void getAllPersonsByHobby() {
//
//    }
//
//    @Test
//    void getPersonCountByHobby() {
//
//    }
//
//    @Test
//    void createHobby() throws HobbyNotFoundException {
//        HobbyDTO hobbyDTO = new HobbyDTO("Music", "Music");
//        HobbyDTO newHobby = facade.createHobby(hobbyDTO);
//        assertEquals(hobbyDTO.getName(), newHobby.getName());
//        assertEquals(3, facade.getAllHobbies().size());
//    }
//
//    @Test
//    void updateHobby() throws HobbyNotFoundException {
//
//        HobbyDTO hobbyDTO = new HobbyDTO("Butterfly breeding","Fun in the sun with butterflies");
//        HobbyDTO hobbyDTO1 = facade.updateHobby(h2.getId(), hobbyDTO);
//        assertEquals("Butterfly breeding", hobbyDTO1.getName());
//    }
//
//    @Test
//    void deleteHobby() throws HobbyNotFoundException {
//        int i = h1.getId();
//        HobbyDTO hobby = facade.deleteHobby(i);
//        assertEquals("Procrastination", hobby.getName(),
//                "Assert that deleted hobby is procrastination");
//        assertEquals(facade.getAllHobbies().size(), 2,
//                "Assert that there's only 2 hobbies left");
//
//    }
}