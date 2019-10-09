package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.CityFacade;
import errorhandling.HobbyNotFoundException;
import facades.FacadeExample;
import facades.HobbyFacade;
import facades.PersonFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@OpenAPIDefinition(
        info = @Info(
                title = "Simple Hobby API",
                version = "0.4",
                description = "Simple API to get info about hobbies.",
                contact = @Contact(name = "Emil Hansen", email = "kontakt mig venligst ikke")
        ),
        tags = {
                @Tag(name = "hobby", description = "API related to Hobby Info")
        },
        servers = {
                @Server(
                        description = "For Local host testing",
                        url = "http://localhost:8080/startcodeoas"
                ),
                @Server(
                        description = "Server API",
                        url = "http://mydroplet"
                )
        }
)
@Path("margarita")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            EMF_Creator.DbSelector.DEV,
            EMF_Creator.Strategy.CREATE);
    private static final FacadeExample FACADE = FacadeExample.getFacadeExample(EMF);
    private static final PersonFacade PERSON_FACADE = PersonFacade.getPersonFacade(EMF);
    private static final CityFacade CITY_FACADE = CityFacade.getCityFacade(EMF);
    private static final HobbyFacade HOBBY_FACADE = HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String connectionMsg() {
        return "{\"msg\":\"Connection established\"}";
    }

    @Path("person/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String getAllPersons() {
        return GSON.toJson(PERSON_FACADE.getAllPersons());
    }

    @Path("hobby/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get specific",
            tags = {"hobby"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "Hobby info"),
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    public String getAllPersonsByHobby(@PathParam("hobby") String hobby) {
        return GSON.toJson(HOBBY_FACADE.getAllPersonsByHobby(hobby));
    }

    @Path("city/city/{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get specific city info",
            tags = {"city"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityInfoDTO.class))),
                    @ApiResponse(responseCode = "200", description = "City info"),
                    @ApiResponse(responseCode = "400", description = "City not found")})
    public String getAllPersonsByCity(@PathParam("city") String city) {
        return GSON.toJson(CITY_FACADE.getPersonsByCity(city));
    }

    @Path("city/zipcode/{zipcode}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get specific city info",
            tags = {"city"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CityInfoDTO.class))),
                    @ApiResponse(responseCode = "200", description = "City info"),
                    @ApiResponse(responseCode = "400", description = "City not found")})
    public String getAllPersonsByZipCode(@PathParam("zipcode") String zipCode) {
        return GSON.toJson(CITY_FACADE.getPersonsByZipCode(zipCode));
    }

    @Path("hobby/{hobby}/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get count of people with hobby",
            tags = {"hobby"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "Count of people with hobby"),
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    public String getCountByHobby(@PathParam("hobby") String hobby) {
        return GSON.toJson(HOBBY_FACADE.getPersonCountByHobby(hobby));
    }

    @Path("zipcode/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String getAllZipCodes() {
        return GSON.toJson(CITY_FACADE.getAllCities());
    }

    @Path("zipcode/{count}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String getCitiesByPersonCount(@PathParam("count") int count) {
        return GSON.toJson(CITY_FACADE.getCitiesByCount(count));
    }

    @Path("person")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Add person",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "Person added"),
                    @ApiResponse(responseCode = "400", description = "Person not added")})
    public String createPerson(@RequestBody(description = "PersonDTO object that needs to be added to the store",
            required = true,
            content = @Content(schema = @Schema(implementation = PersonDTO.class))) PersonDTO content) {
        PersonDTO personDTO = PERSON_FACADE.createPerson(content);
        return GSON.toJson(personDTO);
    }

    @Path("hobby")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Hobby info",
            tags = {"hobby"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my hobbies"),
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    public String createHobby(@RequestBody(description = "HobbyDTO object that needs to be added to the store",
    required = true,
            content = @Content(schema = @Schema(implementation = HobbyDTO.class))) HobbyDTO content) throws HobbyNotFoundException {
        HobbyDTO hobbyDTO = HOBBY_FACADE.createHobby(content);
        return GSON.toJson(hobbyDTO);
    }

    @Path("city")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String createCity(String city) {
        CityInfoDTO cityInfoDTO = GSON.fromJson(city, CityInfoDTO.class);
        //TODO: fix duplicates in db
        return GSON.toJson(CITY_FACADE.createCity(cityInfoDTO));
    }

    @Path("person/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String updatePerson(@PathParam("id") int id, String person) {
        return "personDTO of updated person";
    }

    @Path("city/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String updateCity(@PathParam("id") int id, String city) {
        CityInfoDTO cityInfoDTO = GSON.fromJson(city, CityInfoDTO.class);
        //TODO: fix duplicates in db
        return GSON.toJson(CITY_FACADE.updateCity(id, cityInfoDTO));
    }

    @Path("person/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String deletePerson(@PathParam("id") int id) {
        return "personDTO of deleted person";
    }

    @Path("city/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String deleteCity(@PathParam("id") int id) {
        return GSON.toJson(CITY_FACADE.deleteCity(id));
    }

    @Path("hobby/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Hobby info",
            tags = {"id"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HobbyDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my hobbies"),
                    @ApiResponse(responseCode = "400", description = "Hobby not found")})
    public String deleteHobby(@PathParam("id") int id) throws HobbyNotFoundException {
        return GSON.toJson(HOBBY_FACADE.deleteHobby(id));
    }
}
