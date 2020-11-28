import org.junit.Test;
import org.json.*;
import org.skyscreamer.jsonassert.JSONAssert;
import utils.API;

public class APITest {

    API api = new API();

    @Test
    public void testSignup() throws Exception {
        //user already exists
        JSONObject user = new JSONObject().put("nickname", "marmenaid_");
        user.put("email", "violettaff@gmail.com");
        user.put("password", "1234");
        JSONObject reply = api.signup(user);
        JSONAssert.assertEquals("{response:400}", reply, false);

        //new user registration
//        JSONObject new_user = new JSONObject().put("nickname","test_user");
//        user.put("email","testuser@gmail.com");
//        user.put("password","123");
//        JSONObject reply_2 = API.signup(user);
//        JSONAssert.assertEquals("{response:200}", reply_2, false);
    }

    @Test
    public void testLogin() throws Exception {
        //correct
        JSONObject user = new JSONObject().put("nickname", "marmenaid_");
        user.put("password", "1234");
        JSONObject resp = api.login(user);
        JSONAssert.assertEquals("{\"response\":200,\"userID\":1}", resp, false);

        //no such a user
        JSONObject user_2 = new JSONObject().put("nickname", "ksks");
        user_2.put("password", "12345");
        JSONObject resp_2 = api.login(user_2);
        JSONAssert.assertEquals("{response: 400}", resp_2, false);
    }

    @Test
    public void testGetCities() throws Exception {
        JSONObject resp = api.getCities();
        JSONAssert.assertEquals("{\"cities\":[\"Budapest\", \"Moscow\"],\"response\":200}", resp, false);
    }

    @Test
    public void testGetCityByID() throws Exception {
        JSONObject resp = api.getCityByID(4);
        JSONAssert.assertEquals("{\"cityName\":\"Budapest\",\"response\":200,\"cityInfo\":\"the capital and the most populous city of Hungary, and the ninth-largest city in the European Union by population within city limits.\",\"cityID\":4}", resp, false);

        JSONObject resp_2 = api.getCityByID(3);
        JSONAssert.assertEquals("{response: 400}", resp_2, false);
    }

    @Test
    public void testGetPlaceByID() throws Exception {
        JSONObject resp = api.getPlaceByID(5);
        JSONAssert.assertEquals("{\"placeInfoID\":6,\"placeType\":1,\"response\":200,\"placeID\":5}", resp, false);

        JSONObject resp_2 = api.getCityByID(100);
        JSONAssert.assertEquals("{response: 400}", resp_2, false);
    }

    @Test
    public void testGetPlaceInformationByID() throws Exception {
        JSONObject resp = api.getPlaceInfoByID(5);
        JSONAssert.assertEquals("{\"response\":200,\"placeID\":5,\"placeInformation\":\"magyarorthodoxia.org\",\"placeRating\":4.699999809265137,\"placeName\":\"Orthodox Cathedral of Our Lady\",\"placeAddress\":\"Budapest, Petofi ter 2, 1052 Hungary\"}", resp, false);

        JSONObject resp_2 = api.getPlaceInfoByID(3);
        JSONAssert.assertEquals("{response: 400}", resp_2, false);
    }

    @Test
    public void testUserExistenceByID() throws Exception {
        JSONObject resp = api.checkUserExistenceByID(1);
        JSONAssert.assertEquals("{response: 200}", resp, false);

        JSONObject resp_2 = api.checkUserExistenceByID(100);
        JSONAssert.assertEquals("{response: 400}", resp_2, false);
    }

    @Test
    public void testseePlans() throws Exception {
        JSONObject info = new JSONObject().put("cityName","Marvel");
        info.put("dateOfArrival","20/10/2020");
        info.put("dateOfReturn","21/10/2020");
        info.put("userID", 1);
        JSONObject resp = api.seePlans(info);
        //wrong city name
        JSONAssert.assertEquals("{\"error\":\"No city with such a name\"}", resp, false);

        //wrong user id
        JSONObject info_2 = new JSONObject().put("cityName","Budapest");
        info_2.put("dateOfArrival","20/10/2020");
        info_2.put("dateOfReturn","21/10/2020");
        info_2.put("userID", 0);
        resp = api.seePlans(info_2);
        JSONAssert.assertEquals("{\"error\":\"No user with such ID exist\"}", resp, false);

        //no places for this city
        JSONObject info_3 = new JSONObject().put("cityName","Moscow");
        info_3.put("dateOfArrival","20/10/2020");
        info_3.put("dateOfReturn","21/10/2020");
        info_3.put("userID", 1);
        resp = api.seePlans(info_3);
        JSONAssert.assertEquals("{\"error\":\"No places for this city\"}", resp, false);
    }
}

