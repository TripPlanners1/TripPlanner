package service;

import classes.PlaceInformation;
import dao.PlaceInformationDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.SessionUtil;

import java.util.List;

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
}
