package service;

import classes.Route;
import dao.RouteDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.SessionUtil;

import java.util.List;

public class RouteService extends SessionUtil implements RouteDAO {
    public void saveRoute(Route r) throws Exception {
        openTransactionSession();
        Session session = getSession();
        session.save(r);
        session.getTransaction().commit();
        closeTransactionSession();
    }

    public List getAllRoutes() throws Exception {
        openTransactionSession();
        Session session = getSession();
        Query route_from_query = session.createQuery("FROM Route r");
        //route_from_query.setParameter("id", current_index);
        List currentRoute = route_from_query.list();

        return currentRoute;
    }

    public Route getRouteById(int id) throws Exception {
        openTransactionSession();
        Session session = getSession();
        Query query = session.createQuery("FROM Route r where r.routeID=:id");
        query.setParameter("id", id);
        List route_query = query.list();

        closeTransactionSession();
        if (route_query.size() != 0) {
            Route r = (Route) route_query.get(0);
            return r;
        } else return new Route();//bad request - not registered Route
    }
}
