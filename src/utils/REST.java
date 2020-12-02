package utils;

import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class REST {
    private JSONObject request = new JSONObject();
    private JSONObject apiResponse;
    API api = new API();

    @Path("login/{nickname}/{password}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response login(@PathParam("nickname") String nick, @PathParam("password") String pass) throws Exception {
        request.put("nickname", nick);
        request.put("password", pass);

        apiResponse = api.login(request);
        System.out.println(nick + " " + pass);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("signup/{email}/{nickname}/{password}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response signup(@PathParam("nickname") String nick, @PathParam("email") String email, @PathParam("password") String pass) throws Exception {
        request.put("nickname", nick);
        request.put("password", pass);
        request.put("email", email);

        apiResponse = api.signup(request);
        System.out.println(nick + " " + pass);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("getcities")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response getCities() throws Exception {
        apiResponse = api.getCities();
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("getcitybyID/{ID}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response getcitybyID(@PathParam("ID") String ID) throws Exception {
        apiResponse = api.getCityByID(Integer.parseInt(ID));
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("getplacebyID/{ID}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response getplacebyID(@PathParam("ID") String ID) throws Exception {
        apiResponse = api.getPlaceByID(Integer.parseInt(ID));
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("getplaceinfobyID/{ID}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response getplaceinfobyID(@PathParam("ID") String ID) throws Exception {
        apiResponse = api.getPlaceInfoByID(Integer.parseInt(ID));
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("seePlans")
    @POST
    @Consumes("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    public Response seePlans(String input) throws Exception {
        JSONObject request_ = new JSONObject(input);
        apiResponse = api.seePlans(request_);
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("seeoneplan")
    @POST
    @Consumes("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    public Response seeoneplan(String ids) throws Exception {
        JSONObject request = new JSONObject(ids);
        apiResponse = api.seeOnePlan(request); //ids of places given in seePlans
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

}