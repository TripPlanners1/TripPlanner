package utils;

import classes.PlaceInformation;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import service.PlaceInformationService;

public class Test {
    /*public static void main(String[] args) throws Exception {
        String csvFile = "./mergedLatLong.csv";
        String line = "";
        String cvsSplitBy = ",";
        PlaceInformationService service = new PlaceInformationService();
        SessionUtil util = new SessionUtil();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            List places = service.getIDS();
            util.openTransactionSession();
            Session session = util.getSession();
            int i = 0;
            while ((line = br.readLine()) != null && i<1595) {
                String[] location = line.split(cvsSplitBy);
                //System.out.println("Latitude: " + location[0] + ", Long: " + location[1]);
                String query = "UPDATE PlaceInformation p SET Latitude=:lat, Longitude=:long WHERE p.placeInformationID=:id";
                session.createQuery(query)
                        .setParameter("lat", Float.parseFloat(location[0]))
                        .setParameter("long", Float.parseFloat(location[1]))
                        .setParameter("id", places.get(i))
                        .executeUpdate();
                i++;
            }
            util.closeTransactionSession();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    public static void main(String[] args) throws Exception{
        API api = new API();
        JSONObject location = new JSONObject();
        location.put("latitude", 47.49);
        location.put("longitude", 19.05);
        api.seeNearestPlaces(location);
    }
}