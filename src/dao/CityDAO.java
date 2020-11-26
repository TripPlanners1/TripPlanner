package dao;

import classes.City;
import org.json.JSONArray;

import java.sql.SQLException;

public interface CityDAO {
    JSONArray getAllCities() throws SQLException;

    City getCityByID(int id) throws Exception;

    City getCityByName(String cityName) throws Exception;
}
