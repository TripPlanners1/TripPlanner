package service;

import classes.Users;
import org.hibernate.query.Query;
import utils.HibernateUtil;
import utils.SessionUtil;
import dao.UsersDAO;
import org.hibernate.Session;

import java.sql.*;
import java.util.List;

public class UsersService extends SessionUtil implements UsersDAO {
    boolean response;

    public boolean signup(Users user) throws SQLException {
        //open session with a transaction
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("FROM Users u where u.nickname=:param");
        query.setParameter("param", user.getNickname());

        List q = query.list();
        if (q.size() == 0) {//there is no user with this nickname (no data was found)
            session.save(user);
            session.getTransaction().commit();
            response = true;
        } else response = false;

        //close session with a transaction
        closeTransactionSession();
        return response;
    }

    public Users login(String nickname, String password) throws SQLException {
        //open session with a transaction
        openTransactionSession();

        Session session = getSession();

        Query query = session.createQuery("FROM Users u where u.nickname=:nick and u.userPassword=:pass");
        query.setParameter("nick", nickname);
        query.setParameter("pass", password);
        List q = query.list();

        closeTransactionSession();

        if (q.size() != 0) { //this user is in database
            return (Users)q.get(0);
        }
        else return new Users(); //not registered user
    }

    public Users getUserByID(int id) throws Exception {
        //open session with a transaction
        openTransactionSession();
        Session session = getSession();

        Query query = session.createQuery("FROM Users u where u.userID=:u_id");
        query.setParameter("u_id", id);
        List user_query = query.list();

        closeTransactionSession();
        if (user_query.size() != 0) {
            Users u = (Users) user_query.get(0);
            return u;
        } else return new Users();//bad request - not registered user
    }
}