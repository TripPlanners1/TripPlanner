package utils;

import classes.*;
import dao.UsersDAO;
import org.hibernate.Session;
import org.hibernate.cfg.SetSimpleValueTypeSecondPass;
import org.json.*;
import service.*;


import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class API {
    static int numberOfRoutes = 2;
    static int numberOfPlaces = 5;
    JSONObject reply = new JSONObject();
    boolean response;

    private UsersService userService = new UsersService();
    private CityService cityService = new CityService();
    private PlaceService placeService = new PlaceService();
    private PlaceInformationService placeInformationService = new PlaceInformationService();
    private RouteService routeService = new RouteService();

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public JSONObject signup(JSONObject enteredUser) throws Exception {
        String nickname = enteredUser.getString("nickname");
        String email = enteredUser.getString("email");
        String pass = enteredUser.getString("password");
        Users user = new Users(nickname, email, pass);
        try {
            boolean sp = userService.signup(user);
            if (sp) {
                reply.put("response", 200);
            } else reply.put("response", 400);
            return reply;
        } catch (Exception ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();

            return reply;
        }
    }

    public JSONObject login(JSONObject enteredUser) throws Exception {
        String entered_nickname = enteredUser.getString("nickname");
        String entered_password = enteredUser.getString("password");
        try {
            Users ln = userService.login(entered_nickname, entered_password);
            if (ln.getUserID() != null) {
                reply.put("response", 200);
                reply.put("userID", ln.getUserID());
            } else reply.put("response", 400);
            return reply;
        } catch (Exception ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    public JSONObject getCities() throws Exception {
        try {
            JSONArray cityArr = cityService.getAllCities();
            if (cityArr.length() != 0) {
                reply.put("cities", (Object) cityArr);
                reply.put("response", 200);
            } else reply.put("response", 400);//no cities
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    public JSONObject getCityByID(int id) throws Exception {
        try {
            City c = cityService.getCityByID(id);
            if (c.getCityID() != null) {
                reply.put("cityID", c.getCityID());//everything is correct
                reply.put("cityName", c.getCityName());
                reply.put("cityInfo", c.getCityInfo());
                reply.put("response", 200);
            } else reply.put("response", 400);//bad request - not registered city
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    public JSONObject getPlaceByID(int id) throws Exception {
        try {
            Place p = placeService.getPlaceByID(id);
            if (p.getPlaceID() != null) {
                reply.put("placeID", p.getPlaceID());//everything is correct
                reply.put("placeType", p.getPlaceType());
                reply.put("placeInfoID", p.getPlaceInfoID());
                reply.put("response", 200);
            } else reply.put("response", 400);//bad request - not registered city
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    public JSONObject getPlaceInfoByID(int id) throws Exception {
        try {
            PlaceInformation p = placeInformationService.getPlaceInfoByID(id);
            if (p.getPlaceInformationID() != null) {
                reply.put("placeID", p.getPlaceInformationID());
                reply.put("placeName", p.getName());
                reply.put("placeAddress", p.getFullAdress());
                reply.put("placeInformation", p.getInformation());
                reply.put("placeRating", p.getRating());
                reply.put("response", 200);//everything is correct
            } else reply.put("response", 400);//bad request - not registered city
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    public JSONObject checkUserExistenceByID(int id) throws JSONException {
        try {
            Users u = userService.getUserByID(id);
            if (u.getUserID() != null) {
                reply.put("response", 200);//exist
            } else reply.put("response", 400);//no such user
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    @Transactional
    public JSONObject seeOnePlan(JSONObject ids) throws Exception {
        JSONObject reply = new JSONObject();
        JSONObject final_places = new JSONObject();
        JSONArray routeIDs = new JSONArray();
        //get to know the number of days
        Iterator keysToCopyIterator = ids.keys();
        int number_of_days = 0;
        while (keysToCopyIterator.hasNext()) {
            String key = (String) keysToCopyIterator.next();
            number_of_days++;
        }
        try {
            for (int day = 0; day < number_of_days; day++) {
                routeIDs = ids.getJSONArray(String.valueOf(day));
                for (int i = 0; i < routeIDs.length(); i++) {
                    int current_index = (int) routeIDs.get(i);
                    Route r = routeService.getRouteById(current_index);
                    if (r.getRouteID() != null) {
                        Place current_place = r.getPlace();
                        JSONObject current_place_info = getPlaceInfoByID(current_place.getPlaceInfoID());
                        JSONArray place_info = new JSONArray().put(current_place.getPlaceType());
                        place_info.put(current_place_info.get("placeName"));
                        place_info.put(current_place_info.get("placeAddress"));
                        place_info.put(current_place_info.get("placeInformation"));
                        place_info.put(current_place_info.get("placeRating"));
                        final_places.put(String.valueOf(i), (Object) place_info);
                        System.out.println(final_places);
                    }
                }
                reply.put(String.valueOf(day), final_places);
                //System.out.println(reply);
                final_places = new JSONObject();
            }
            reply.put("response", 200);
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return reply;
        }
    }

    public JSONObject seePlans(JSONObject travelInfo) throws Exception {
        JSONObject routesIDs = new JSONObject();
        int number_of_needed_places = 0;
        int number_of_restaurants = 0;
        int number_of_parks = 0;
        try {
            String entered_cityName = travelInfo.getString("cityName");
            String entered_arrival = travelInfo.getString("dateOfArrival");
            String entered_return = travelInfo.getString("dateOfReturn");
            int userID = travelInfo.getInt("userID");

            LocalDate date1 = LocalDate.parse(entered_arrival, dtf);
            LocalDate date2 = LocalDate.parse(entered_return, dtf);

            int number_of_days = (int) (long) ChronoUnit.DAYS.between(date1, date2);
            System.out.println("Days between: " + number_of_days);

            //check correctness of user ID
            JSONObject user = checkUserExistenceByID(userID);
            if (user.getInt("response") != 200) {
                reply.put("error", "No user with such ID exist");
                return reply;
            }

            if (number_of_days <= 3) {
                number_of_needed_places = 1; //each day visit N historical places
                number_of_restaurants = 1;
                number_of_parks = 0;
            }
            if (number_of_days > 3 && number_of_days <= 7) {
                number_of_needed_places = 2;
                number_of_restaurants = 2;
                number_of_parks = 2;
            }
            if (number_of_days > 7) {
                number_of_needed_places = 2;
                number_of_restaurants = 2;
                number_of_parks = 4;
            }

            //take city id by entered CityName
            City city = cityService.getCityByName(entered_cityName);

            //is there a city with such a name?
            if (city.getCityID() == null) {
                reply.put("error", "No city with such a name");
                return reply;
            }

            //take places for this City
            List places = placeService.getPlacesForCity(city);
            if (places.size() == 0) {
                reply.put("error", "No places for this city");
                return reply;
            }

            int saved_places = 0, saved_rest = 0, saved_parks = 0;
            //Random rand = new Random();
            int counter = 0;
            //for now: take N random places
            ArrayList<Integer> randList = new ArrayList<Integer>();
            for (int i = 0; i < places.size(); i++) {
                randList.add(i);
            }
            JSONArray ids = new JSONArray();
            Collections.shuffle(randList);
            //MAIN ALGORITHM
            for (int route_i = 0; route_i < numberOfRoutes; route_i++) {
                //System.out.println("Route: " + route_i);
                for (int day_i = 0; day_i < number_of_days; day_i++) {
                    //System.out.println("Day: " + day_i);
                    while (number_of_needed_places >= saved_places
                            && number_of_restaurants >= saved_rest
                            && number_of_parks >= saved_parks) {
                        int index = randList.get(counter);
                        Place place = (Place) places.get(index);
                        //System.out.println(index + ", " + place.getPlaceID());
                        if (place.getPlaceType() == 1) {//if it is a historical place
                            saved_places++;
                        }
                        if (place.getPlaceType() == 2) {//if it is a restaurant
                            saved_rest++;
                        }
                        if (place.getPlaceType() == 3) {//if it is a park
                            saved_parks++;
                        }
                        Route r = new Route(city, place, day_i, route_i);
                        Users u = new Users();
                        u.setUserID(userID);
                        r.setUser(u);
                        routeService.saveRoute(r);
                        ids.put(r.getRouteID());
                        counter++;
                    }
                    saved_places = 0;
                    saved_rest = 0;
                    saved_parks = 0;
                    routesIDs.put(String.valueOf(day_i), (Object) ids);
                    ids = new JSONArray();
                }
                //routesIDs.put(String.valueOf(route_i), (Object) ids);
                //ids = new JSONArray();
            }
            routesIDs.put("response", 200);
            return routesIDs;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            System.out.println(ex.getMessage());
            return reply;
        }
    }

    public JSONObject seeNearestPlaces(JSONObject location) throws Exception {
        try {
            Double user_lat = location.getDouble("latitude");
            Double user_long = location.getDouble("longitude");

            List place = placeInformationService.getByDistance(user_lat, user_long);

            if (place.size() != 0) {
                for (int i = 0; i < numberOfPlaces; i++) {
                    PlaceInformation current_place = (PlaceInformation)place.get(i);
                    JSONArray place_info = new JSONArray();
                    place_info.put(current_place.getName());
                    place_info.put(current_place.getInformation());
                    place_info.put(current_place.getRating());
                    place_info.put(current_place.getFullAdress());
                    reply.put(String.valueOf(i), (Object) place_info);
                }
            }
            reply.put("response", 200);
            return reply;
        } catch (Throwable ex) {
            reply.put("response", 400);
            reply.put("errorClass", ex.getClass()); //exception
            reply.put("errorMessage", ex.getMessage());
            System.out.println(ex.getMessage());
            return reply;
        }
    }
}

