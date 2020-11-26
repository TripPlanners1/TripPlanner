package dao;

import classes.PlaceInformation;

public interface PlaceInformationDAO {
    PlaceInformation getPlaceInfoByID(int id) throws Exception;
}
