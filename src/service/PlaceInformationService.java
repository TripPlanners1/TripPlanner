package service;

import classes.PlaceInformation;
import dao.PlaceInformationDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.SessionUtil;

import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PlaceInformationService extends SessionUtil implements PlaceInformationDAO {

    public PlaceInformation getPlaceInfoByID(int id) throws Exception {
        //open session with a transaction
        openTransactionSession();
        Session session = getSession();

        Query query = session.createQuery("FROM PlaceInformation u where u.placeInformationID=:id");
        query.setParameter("id", id);
        List place = query.list();


        closeTransactionSession();
        if (place.size() != 0) {
            PlaceInformation p = (PlaceInformation) place.get(0);
            return p;
        } else return new PlaceInformation();//bad request - not registered city
    }

    public List getIDS() throws Exception {
        //open session with a transaction
        openTransactionSession();
        Session session = getSession();

        Query query = session.createQuery("SELECT placeInformationID FROM PlaceInformation u");
        List place = query.list();

        closeTransactionSession();
        if (place.size() != 0) {
            return place;
        } else return null;
    }

    public List getByDistance(Double user_lat, Double user_long) throws Exception {
        openTransactionSession();
        Session session = getSession();

        String distance = "ABS(dbo.DictanceKM(:lat1, Latitude, :long1, Longitude))";
        String query = "FROM PlaceInformation ORDER BY " + distance;

        Query q = session.createQuery(query);
        q.setParameter("lat1", user_lat.floatValue());
        q.setParameter("long1", user_long.floatValue());
        List place = q.list();
        closeTransactionSession();
        return place;
    }
}
