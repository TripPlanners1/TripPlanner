package utils;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;

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

    @Path("seePlans/{info}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response seePlans(@PathParam("info") JSONObject travelInfo) throws Exception {
        apiResponse = api.seePlans(travelInfo); //travelInfo - cityName, dateOfArrival, dateOfReturn, userID
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

    @Path("seeoneplan/{IDS}")
    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response seeoneplan(@PathParam("IDS") JSONObject ids) throws Exception {
        apiResponse = api.seeOnePlan(ids); //ids of places given in seePlans
        System.out.println(apiResponse);
        String result = "" + apiResponse;
        return Response.status(apiResponse.getInt("response")).entity(result).build();
    }

}