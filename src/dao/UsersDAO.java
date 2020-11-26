package dao;

import classes.Users;

import java.sql.SQLException;

public interface UsersDAO {

    boolean signup(Users user) throws SQLException;
    boolean login(String nickname, String password) throws SQLException;
    Users getUserByID(int id) throws Exception;
}
