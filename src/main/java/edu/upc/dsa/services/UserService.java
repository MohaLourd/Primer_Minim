package edu.upc.dsa.services;

import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Api(value = "/users", description = "Endpoint to User Service")
@Path("/users")
public class UserService {

    private UserManager um;

    public UserService() {
        this.um = UserManagerImpl.getInstance();
        this.um.addUser("1", "John", "Doe", "john.doe@example.com", "1990-01-01");
        this.um.addUser("2", "Jane", "Smith", "jane.smith@example.com", "1992-02-02");

        this.um.addUser("3", "Alice", "Johnson", "alice.johnson@example.com", "1985-03-03");
        this.um.addUser("4", "Bob", "Brown", "bob.brown@example.com", "1988-04-04");
        this.um.addUser("5", "Charlie", "Davis", "charlie.davis@example.com", "1991-05-05");

        this.um.addPointOfInterest("70", "80", ElementType.BRIDGE);
        this.um.addPointOfInterest("90", "100", ElementType.TREE);
        this.um.addPointOfInterest("110", "120", ElementType.DOOR);
        this.um.addPointOfInterest("10", "20", ElementType.DOOR);
        this.um.addPointOfInterest("30", "40", ElementType.TREE);
        this.um.addPointOfInterest("50", "60", ElementType.BRIDGE);
        this.um.addPointOfInterest("30", "80", ElementType.BRIDGE);
        this.um.addPointOfInterest("45", "100", ElementType.TREE);
        this.um.addPointOfInterest("1", "120", ElementType.DOOR);
        this.um.addPointOfInterest("107", "20", ElementType.DOOR);
        this.um.addPointOfInterest("39", "40", ElementType.TREE);
        this.um.addPointOfInterest("40", "60", ElementType.POTION);


    }

    private void extracted() {
        this.um.addPointOfInterest("40", "60", ElementType.BRIDGE);
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "Retrieve all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = this.um.findAll();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a User", notes = "Retrieve a user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        User u = this.um.getUser(id);
        if (u == null) return Response.status(404).build();
        else return Response.status(200).entity(u).build();
    }

    @POST
    @ApiOperation(value = "create a new User", notes = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {
        if (user.getName() == null || user.getSurname() == null || user.getEmail() == null || user.getBirthDate() == null) {
            return Response.status(500).entity(user).build();
        }
        this.um.addUser(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getBirthDate());
        return Response.status(201).entity(user).build();
    }

    @PUT
    @ApiOperation(value = "update a User", notes = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/")
    public Response updateUser(User user) {
        User u = this.um.updateUser(user);
        if (u == null) return Response.status(404).build();
        return Response.status(200).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        User u = this.um.getUser(id);
        if (u == null) return Response.status(404).build();
        else this.um.deleteUser(id);
        return Response.status(200).build();
    }


    @GET
    @ApiOperation(value = "get all Users sorted", notes = "Retrieve all users sorted by surname and name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
    })
    @Path("/sorted")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersSorted() {
        List<User> users = this.um.findAllSorted();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get User by ID", notes = "Retrieve a user by their identifier")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/byId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") String id) {
        User u = this.um.getUserById(id);
        if (u == null) return Response.status(404).build();
        else return Response.status(200).entity(u).build();
    }

    @POST
    @ApiOperation(value = "create a new PointOfInterest", notes = "Create a new point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PointOfInterest.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/points")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPointOfInterest(PointOfInterest poi) {
        if (poi.getType() == null) {
            return Response.status(500).entity("Type is required").build();
        }
        this.um.addPointOfInterest(String.valueOf(poi.getX()), String.valueOf(poi.getY()), poi.getType());
        return Response.status(201).entity(poi).build();
    }


    @POST
    @ApiOperation(value = "register User at PointOfInterest", notes = "Register a user at a point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User or PointOfInterest not found")
    })
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUserAtPoint(@QueryParam("userId") String userId, @QueryParam("x") String x, @QueryParam("y") String y) {
        try {
            this.um.registerUserAtPoint(userId, x, y);
            return Response.status(200).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @ApiOperation(value = "get PointsOfInterest for a User", notes = "Retrieve points of interest for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PointOfInterest.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{userId}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPointsOfInterest(@PathParam("userId") String userId) {
        try {
            List<PointOfInterest> points = this.um.getUserPointsOfInterest(userId);
            GenericEntity<List<PointOfInterest>> entity = new GenericEntity<List<PointOfInterest>>(points) {};
            return Response.status(200).entity(entity).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @ApiOperation(value = "get Users by PointOfInterest", notes = "Retrieve users by point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "PointOfInterest not found")
    })
    @Path("/points/{x}/{y}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByPointOfInterest(@PathParam("x") String x, @PathParam("y") String y) {
        try {
            List<User> users = this.um.getUsersByPointOfInterest(x, y);
            GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
            return Response.status(200).entity(entity).build();
        } catch (IllegalArgumentException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }


    @GET
    @ApiOperation(value = "get PointsOfInterest by Type", notes = "Retrieve points of interest by type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PointOfInterest.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No points of interest found for the specified type")
    })
    @Path("/points/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPointsOfInterestByType(@PathParam("type") ElementType type) {
        List<PointOfInterest> points = this.um.getPointsOfInterestByType(type);
        if (points.isEmpty()) {
            return Response.status(404).entity("No points of interest found for the specified type").build();
        }
        GenericEntity<List<PointOfInterest>> entity = new GenericEntity<List<PointOfInterest>>(points) {};
        return Response.status(200).entity(entity).build();
    }
}