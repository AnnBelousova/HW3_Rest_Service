package com.example.hw3_rest_service.dao;

import com.example.hw3_rest_service.entity.Claim;
import com.example.hw3_rest_service.exception.ExceptionDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anna Belousova
 * Claim DAO класс предоставляющий базовую реализацию CRUD операций с использованием JDBC
 */
public class ClaimDao extends DaoBase {
    private static final String SELECT_CLAIMS_BY_EMAIL = "SELECT * FROM public.claims c INNER JOIN public.users u ON u.id=c.user_id WHERE email=(?)";
    private static final String SELECT_ALL_CLAIMS = "SELECT * FROM public.claims";
    private static final String UPDATE_CLAIM = "UPDATE public.claims SET description=(?) WHERE id=(?)";
    private static final String INSERT_CLAIM = "INSERT INTO public.claims" + " (description, user_id) VALUES (?,?)";
    private static final String DELETE_CLAIM = "DELETE FROM public.claims WHERE id=(?)";
    private static final String SELECT_PRODUCT_ID_BY_CLAIM_ID = "SELECT product_id FROM claim_product WHERE claim_id = (?)";

    /**
     * Метод, который возвращает список claims по email
     *
     * @param email
     * @return List<Claim>
     */
    public List<Claim> getClaimListByEmail(String email) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLAIMS_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            return getClaimList(preparedStatement);
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    /**
     * Метод, который возвращает все claims из БД
     *
     * @return List<Claim>
     */
    public List<Claim> getAllClaims() {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLAIMS)) {
            return getClaimList(preparedStatement);
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    /**
     * Метод, который возвращает все claims из БД
     *
     * @param preparedStatement
     * @return List<Claim>
     */
    private List<Claim> getClaimList(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Claim> claimListAll = new ArrayList<>();
        while (resultSet.next()) {
            Claim claim = new Claim();
            claim.setId(resultSet.getInt(1));
            claim.setDescription(resultSet.getString(2));
            claim.setUser_id(resultSet.getInt(3));
            claim.setProductId(getListProductId(claim.getId()));
            claimListAll.add(claim);
        }
        resultSet.close();
        return claimListAll;
    }

    /**
     * Метод, который возвращает все claims из БД
     *
     * @param claim
     * @return Сlaim
     */
    public Claim updateClaim(Claim claim) {
        if (claim.getDescription() != null) {
            Connection connection = getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLAIM)) {
                preparedStatement.setString(1, claim.getDescription());
                preparedStatement.setInt(2, claim.getId());
                int count = preparedStatement.executeUpdate();
                if (count == 0) {
                    return null;
                }
            } catch (SQLException e) {
            }
        }
        if (claim.getProductId() != null && claim.getProductId().size() != 0) {
            deleteClaimProduct(claim.getId());
            insertToClaimProduct(claim);
        }
        return claim;
    }

    /**
     * Метод, который добавляет в БД новую claim
     *
     * @param claim
     * @return Claim
     */
    public Claim insertClaim(Claim claim) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLAIM, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, claim.getDescription());
            preparedStatement.setInt(2, claim.getUser_id());
            int count = preparedStatement.executeUpdate();
            if (count == 0) {
                return null;
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                claim.setId(generatedKeys.getInt(1));
            }
            generatedKeys.close();
            insertToClaimProduct(claim);
            return claim;
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * Метод, который удаляет claim по id
     *
     * @param id
     * @return boolean
     */
    public boolean deleteClaim(int id) {
        Connection connection = getConnection();
        deleteClaimProduct(id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLAIM)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    /**
     * Метод, который возвращает список id-шников продуктов по claim id
     *
     * @param claimId
     * @return List<Integer>
     */
    private List<Integer> getListProductId(int claimId) {
        List<Integer> productIdList = new ArrayList<>();
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_ID_BY_CLAIM_ID)) {
            preparedStatement.setInt(1, claimId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productIdList.add(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new ExceptionDao(e.getMessage());
        }
        return productIdList;
    }

    /**
     * Метод, который добавляет в таблицу claim_product id-шники продуктов и claims при обновлениии и добавлении claim
     *
     * @param claim
     */
    private void insertToClaimProduct(Claim claim) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.claim_product(product_id, claim_id)VALUES (?, ?); ")) {
            for (Integer productId : claim.getProductId()) {
                preparedStatement.setInt(1, productId);
                preparedStatement.setInt(2, claim.getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new ExceptionDao(e.getMessage());
        }
    }

    /**
     * Метод, который удаляет из таблицу claim_product id-шники продуктов и claims обновлении и удалении claim
     *
     * @param claimId
     * @return void
     */
    private void deleteClaimProduct(int claimId) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.claim_product WHERE claim_id = (?)")) {
            preparedStatement.setInt(1, claimId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionDao(e.getMessage());
        }
    }

    /**
     * Метод, который возвращает claim по id
     *
     * @param claimId
     * @return Claim
     */
    public Claim getClaimById(int claimId) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.claims WHERE id=(?)")) {
            preparedStatement.setInt(1, claimId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Claim claim = new Claim();
                    claim.setId(resultSet.getInt(1));
                    claim.setDescription(resultSet.getString(2));
                    claim.setUser_id(resultSet.getInt(3));
                    claim.setProductId(getListProductId(claim.getId()));
                    return claim;
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
