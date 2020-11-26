package dao;

import classes.Route;

import java.util.List;

public interface RouteDAO {
    void saveRoute(Route r) throws Exception;

    List getAllRoutes() throws Exception;

    Route getRouteById(int id) throws Exception;
}
