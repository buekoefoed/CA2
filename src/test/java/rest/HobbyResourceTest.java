package rest;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
class HobbyResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    ;
    private static PersonEntity p1, p2, p3;
    private static CityInfoEntity c1, c2, c3;
    private static PersonDTO p1DTO, p2DTO, p3DTO;
    private static CityInfoDTO c1DTO, c2DTO, c3DTO;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    void setUp() {
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
    void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/margarita").then().statusCode(200);
    }

    @Test
    void testEndpoint() {
        given()
                .contentType("application/json")
                .get("/margarita/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Connection established"));
    }

    @Test
    void getAllPersonsSize() {
        given()
                .contentType("application/json")
                .get("margarita/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("", hasSize(3));
    }

    @Test
    void getAllPersonsContent() {
        List<PersonDTO> personDTOS = given()
                .contentType("application/json")
                .get("margarita/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", PersonDTO.class);

        assertThat(personDTOS, containsInAnyOrder(p1DTO, p2DTO, p3DTO));
    }

    @Test
    void getAllPersonsByHobby() {
        List<PersonDTO> personDTOS = given()
                .contentType("application/json")
                .get("margarita/hobby/" + p3.getHobbies().get(0).getName()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", PersonDTO.class);

        assertThat(personDTOS, containsInAnyOrder(p3DTO));
    }

    @Test
    void getAllPersonsByCity() {
        List<PersonDTO> personDTOS = given()
                .contentType("application/json")
                .get("margarita/city/city/" + p1.getAddress().getCityInfo().getCity()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", PersonDTO.class);

        assertThat(personDTOS, containsInAnyOrder(p1DTO));
    }

    @Test
    void getAllPersonsByZipCode() {
        List<PersonDTO> personDTOS = given()
                .contentType("application/json")
                .get("margarita/city/zipcode/" + p1.getAddress().getCityInfo().getZipCode()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", PersonDTO.class);

        assertThat(personDTOS, containsInAnyOrder(p1DTO));
    }

    @Test
    void getCountByHobby() {
        int count = given()
                .contentType("application/json")
                .get("margarita/hobby/" + p1.getHobbies().get(0).getName() + "/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(Integer.class);

        assertThat(count, equalTo(1));
    }

    @Test
    void getAllZipCodes() {
        List<CityInfoDTO> cities = given()
                .contentType("application/json")
                .get("margarita/zipcode/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", CityInfoDTO.class);

        assertThat(cities, containsInAnyOrder(c1DTO, c2DTO, c3DTO));
    }

    @Test
    void getCitiesByPersonCount() {
        List<CityInfoDTO> cities = given()
                .contentType("application/json")
                .get("margarita/zipcode/" + "0").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", CityInfoDTO.class);

        assertThat(cities, containsInAnyOrder(c1DTO, c2DTO, c3DTO));
    }

    @Test
    void createPerson() {
        PersonEntity p4 = new PersonEntity();

        // <editor-fold defaultstate="collapsed" desc="Test data: p4. Click on the + sign to expand.">
        p4.setEmail("bart@test.com");
        p4.setFirstName("Bart");
        p4.setLastName("Simpson");

        HobbyEntity h4 = new HobbyEntity();
        h4.setName("Skating");
        h4.setDescription("Skating around town");
        p4.addHobby(h4);

        PhoneEntity ph4 = new PhoneEntity();
        ph4.setNumber("1234");
        ph4.setDescription("Private");
        p4.addPhone(ph4);

        CityInfoEntity c4 = new CityInfoEntity();
        c4.setCity("Springfield");
        c4.setZipCode("65808");

        AddressEntity a4 = new AddressEntity();
        a4.setStreet("Evergreen Terrace");
        a4.setAdditionalInfo("742");
        p4.addAddress(a4);
        c4.addAddress(a4);
        // </editor-fold>

        PersonDTO p4DTO = new PersonDTO(p4);

        PersonDTO person = given()
                .contentType("application/json")
                .body(p4DTO)
                .when()
                .post("margarita/person").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(PersonDTO.class);

        assertThat(person, equalTo(p4DTO));
    }

    @Test
    void createHobby() {
        HobbyDTO h4DTO = new HobbyDTO();
        h4DTO.setName("Football");
        h4DTO.setDescription("Playing the game football");

        HobbyDTO hobby = given()
                .contentType("application/json")
                .body(h4DTO)
                .when()
                .post("margarita/hobby").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(HobbyDTO.class);

        assertThat(hobby, equalTo(h4DTO));
    }

    @Test
    void createCity() {
        CityInfoDTO c4DTO = new CityInfoDTO();
        c4DTO.setCity("Frederiksberg");
        c4DTO.setZipCode("2000");

        CityInfoDTO city = given()
                .contentType("application/json")
                .body(c4DTO)
                .when()
                .post("margarita/city").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(CityInfoDTO.class);

        assertThat(city, equalTo(c4DTO));
    }

    @Test
    void updatePerson() {
        p1DTO.setEmail("bue@mail.com");
        p1DTO.getPhones().get(0).setDescription("Private");
        p1DTO.getAddress().setStreet("Betty Nansens Allé");
        p1DTO.getAddress().setAdditionalInfo("51");
        p1DTO.getCityInfoDTO().setCity("Frederiksberg");
        p1DTO.getCityInfoDTO().setZipCode("2000");

        PersonDTO person = given()
                .contentType("application/json")
                .body(p1DTO)
                .when()
                .put("margarita/person/" + p1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(PersonDTO.class);

        assertThat(person, equalTo(p1DTO));
    }

    @Test
    void updateCity() {
        c1DTO.setZipCode("2000");
        c1DTO.setCity("Frederiksberg");

        CityInfoDTO city = given()
                .contentType("application/json")
                .body(c1DTO)
                .when()
                .put("margarita/city/" + c1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(CityInfoDTO.class);

        assertThat(city, equalTo(c1DTO));
    }

    @Test
    void deletePerson() {
        PersonDTO person = given()
                .contentType("application/json")
                .delete("/margarita/person/" + p1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(PersonDTO.class);

        List<PersonDTO> personDTOS = given()
                .contentType("application/json")
                .get("margarita/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", PersonDTO.class);

        assertThat(person, equalTo(p1DTO));
        assertThat(personDTOS, containsInAnyOrder(p2DTO, p3DTO));
        assertThat(personDTOS, not(containsInAnyOrder(p1DTO)));
    }

    @Test
    void deleteCity() {
        /*
        CityInfoDTO city = given()
                .contentType("application/json")
                .delete("/margarita/city/" + c1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(CityInfoDTO.class);

        List<CityInfoDTO> cityInfoDTOS = given()
                .contentType("application/json")
                .get("margarita/city/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .body().jsonPath().getList("", CityInfoDTO.class);

        assertThat(city, equalTo(c1DTO));
        assertThat(cityInfoDTOS, containsInAnyOrder(c2DTO, c3DTO));
        assertThat(cityInfoDTOS, not(containsInAnyOrder(c1DTO)));
        */
    }

    @Test
    void deleteHobby() {
    }
}
