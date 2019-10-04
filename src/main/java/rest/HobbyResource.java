package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("margarita")
public class HobbyResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            EMF_Creator.DbSelector.DEV,
            EMF_Creator.Strategy.CREATE);
    private static final FacadeExample FACADE =  FacadeExample.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String connectionMsg() {
        return "{\"msg\":\"Connection established\"}";
    }

    @Path("person/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        return "All persons";
    }

    @Path("hobby/{hobby}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersonsByHobby(@PathParam("hobby") String hobby) {
        return "All persons with a given hobby";
    }

    @Path("city/{city}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersonsByCity(@PathParam("city") String city) {
        return "All persons from a given city";
    }

    @Path("hobby/{hobby}/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCountByHobby(@PathParam("hobby") String hobby) {
        return "Count of a given hobby";
    }

    @Path("zipcode/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllZipCodes() {
        return "All zip codes";
    }

    @Path("zipcode/{count}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCitiesByPersonCount(@PathParam("count") int count) {
        return "All cities with count or more people";
    }

    @Path("person")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createPerson(String person) {
        return "PersonDTO with ID from DB";
    }

    @Path("hobby")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createHobby(String hobby) {
        return "HobbyDTO with ID from DB";
    }

    @Path("city")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createCity(String city) {
        return "CityDTO with ID from DB";
    }

    @Path("person/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String updatePerson(@PathParam("id") int id, String person) {
        return "personDTO of updated person";
    }

    @Path("person/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") int id) {
        return "personDTO of deleted person";
    }
}
