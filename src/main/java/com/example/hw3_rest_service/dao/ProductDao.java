package com.example.hw3_rest_service.dao;

import com.example.hw3_rest_service.entity.Product;
import com.example.hw3_rest_service.exception.ExceptionDao;

import java.sql.*;

/**
 * @author Anna Belousova
 * Product DAO класс c методом получения Product по id
 */
public class ProductDao extends DaoBase {

    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM public.product WHERE id = (?)";

    /**
     * Метод, который возвращает Product по id
     *
     * @param id
     * @return Product
     */
    public Product getProductById(int id) {
        Connection connection = getConnection();
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getInt(1));
                    product.setName(resultSet.getString(2));
                    product.setWeight(resultSet.getInt(3));
                }
            }
        } catch (Exception e) {
            throw new ExceptionDao(e.getMessage());
        }
        return product;
    }
}
