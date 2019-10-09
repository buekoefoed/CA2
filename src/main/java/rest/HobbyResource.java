package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.FacadeExample;
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
        return "All persons with a given hobby";
    }

    @Path("city/{city}")
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
        return "All persons from a given city";
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
        return "Count of a given hobby";
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
        return "All zip codes";
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
        return "All cities with count or more people";
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
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String createHobby(String hobby) {
        return "HobbyDTO with ID from DB";
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
        return "CityDTO with ID from DB";
    }

    @Path("person/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Update Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "Person updated"),
                    @ApiResponse(responseCode = "400", description = "Person not found")})
    public String updatePerson(@PathParam("id") int id, @RequestBody(description = "PersonDTO to replace original with",
            required = true,
            content = @Content(schema = @Schema(implementation = PersonDTO.class))) PersonDTO content) {
        return GSON.toJson(PERSON_FACADE.updatePerson(id, content));
    }

    @Path("person/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Operation(summary = "Get Person info",
            tags = {"person"},
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "All of my people"),
                    @ApiResponse(responseCode = "400", description = "Persons not found")})
    public String deletePerson(@PathParam("id") int id) {
        return GSON.toJson(PERSON_FACADE.deletePerson(id));
    }
}
