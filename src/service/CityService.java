package service;

import classes.City;
import classes.Users;
import dao.CityDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.SessionUtil;

import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;


@RequestScoped
public class CityService extends SessionUtil implements CityDAO {
    JSONArray cityArr = new JSONArray();
    JSONObject reply = new JSONObject();

    public JSONArray getAllCities() throws SQLException {
        //open session with a transaction
        openTransactionSession();

        Session session = getSession();

        Query query = session.createQuery("FROM City");
        List cities = query.list();
        if (cities.size() != 0) {
            for (int i = 0; i < cities.size(); i++) {
                City c = (City) cities.get(i);
                cityArr.put(c.getCityName());
            }

            //close session with a transaction
            closeTransactionSession();
        }
        return cityArr;
    }

    public City getCityByID(int id) throws Exception {
        //open session with a transaction
        openTransactionSession();
        Session session = getSession();

        Query query = session.createQuery("FROM City u where u.cityID=:id");
        query.setParameter("id", id);
        List city_query = query.list();

        closeTransactionSession();
        if (city_query.size() != 0) {
            City c = (City) city_query.get(0);
            return c;
        } else return new City();//bad request - not registered city
    }

    public City getCityByName(String cityName) throws Exception {
        //open session with a transaction
        openTransactionSession();
        Session session = getSession();

        Query getCityIDQuery = session.createQuery("FROM City u where u.cityName=:name");
        getCityIDQuery.setParameter("name", cityName);
        List city_query = getCityIDQuery.list();

        closeTransactionSession();
        if (city_query.size() != 0) {
            City c = (City) city_query.get(0);
            return c;
        } else return new City();//bad request - not registered city
    }
}
