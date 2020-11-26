package dao;

import classes.City;
import classes.Place;

import java.util.List;

public interface PlaceDAO {
    Place getPlaceByID(int id) throws Exception;
    List getPlacesForCity(City city)throws Exception;
}
