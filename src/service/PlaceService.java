package service;

import classes.City;
import classes.Place;
import dao.PlaceDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.SessionUtil;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;

@FacesConfig
@ApplicationScoped
public class PlaceService extends SessionUtil implements PlaceDAO {

    public Place getPlaceByID(int id) throws Exception {
        //open session with a transaction
        openTransactionSession();
        Session session = getSession();

        Query query = session.createQuery("FROM Place u where u.placeID=:id");
        query.setParameter("id", id);
        List place_query = query.list();

        closeTransactionSession();
        if (place_query.size() != 0) {
            Place p = (Place) place_query.get(0);
            return p;
        } else return new Place();//bad request - not registered Place
    }

    public List getPlacesForCity(City city)throws Exception{
        openTransactionSession();
        Session session = getSession();

        Query getPlaces = session.createQuery("FROM Place u where u.city=:c");
        getPlaces.setParameter("c", city);
        List places = getPlaces.list(); //place_id, city_id, place_info_id, place_type

        closeTransactionSession();

        return places;
    }
}
