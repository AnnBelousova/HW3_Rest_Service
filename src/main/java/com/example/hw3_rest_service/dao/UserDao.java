package com.example.hw3_rest_service.dao;

import com.example.hw3_rest_service.entity.User;
import com.example.hw3_rest_service.exception.ExceptionDao;

import java.sql.*;

/**
 * @author Anna Belousova
 * User DAO класс c методом добавления нового пользователя
 */
public class UserDao extends DaoBase {
    private static final String INSERT_USER = "INSERT INTO public.users" + " (name, email) VALUES " + "(?, ?)";
    private static final String SELECT_USER_EXISTS = "SELECT * FROM public.users where email=" + "(?)";
    private static final String SELECT_USER_ID = "SELECT * FROM public.users where email=" + "(?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM public.users where id=(?)";

    /**
     * Метод, который добавляет нового пользователя
     *
     * @param user
     * @return
     */
    public void insertUser(User user) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Метод, который проверяет, что пользоатель с указанным email есть в базе
     *
     * @param email
     * @return boolean
     * @throws SQLException
     */
    public boolean checkUser(String email) throws SQLException {
        boolean check = false;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_EXISTS);
        preparedStatement.setString(1, email);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                if ((resultSet.getString(3).equals(email))) {
                    check = true;
                } else {
                    check = false;
                }
            }
        }
        return check;
    }

    /**
     * Метод получения пользователя по id
     *
     * @param id
     * @return User
     */
    public User getUserById(int id) {
        User user = null;
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setName(resultSet.getString(2));
                    user.setEmail(resultSet.getString(3));
                }
            }
        } catch (Exception e) {
            throw new ExceptionDao(e.getMessage());
        }
        return user;
    }
}

