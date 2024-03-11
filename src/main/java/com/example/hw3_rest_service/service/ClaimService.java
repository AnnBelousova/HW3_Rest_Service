package com.example.hw3_rest_service.service;

import com.example.hw3_rest_service.dao.ClaimDao;
import com.example.hw3_rest_service.dao.ProductDao;
import com.example.hw3_rest_service.dao.UserDao;
import com.example.hw3_rest_service.entity.Claim;
import com.example.hw3_rest_service.entity.Product;
import com.example.hw3_rest_service.mapper.MapperClaim;
import com.example.hw3_rest_service.mapper.MapperProduct;
import com.example.hw3_rest_service.mapper.MapperUser;
import com.example.hw3_rest_service.model.*;
import com.example.hw3_rest_service.model.dto.ClaimDto;
import com.example.hw3_rest_service.model.dto.ProductDto;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс сервиса (бизнес-логика) реализации GRUD операций
 */

public class ClaimService {
    ClaimDao claimDao = new ClaimDao();
    UserDao userDao = new UserDao();
    ProductDao productDao = new ProductDao();
    Gson gson = new Gson();
    MapperClaim mapperClaim = new MapperClaim();
    MapperUser mapperUser = new MapperUser();
    MapperProduct mapperProduct = new MapperProduct();

    /**
     * Конструктор с параметрами
     *
     * @param claimDao
     * @param userDao
     * @param productDao
     */
    public ClaimService(ClaimDao claimDao, UserDao userDao, ProductDao productDao) {
        this.claimDao = claimDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    /**
     * Конструктор без параметров
     */
    public ClaimService() {
    }

    /**
     * Метод получения списка claims по email пользователя
     * @param email
     * @return возвращает DTO (список claims)
     */
    public String getClaimListByEmail(String email) {
        List<Claim> claimList = claimDao.getClaimListByEmail(email);
        List<ClaimDto> claimDtoList = claimList.stream().map(item -> {
            return claimToClaimDto(item);
        }).collect(Collectors.toList());
        ResponseClaimList responseClaimList = new ResponseClaimList(claimDtoList);
        return gson.toJson(responseClaimList);
    }

    /**
     * Метод получения всего списка claims
     * @return возвращает DTO (список claims)
     */
    public String getAllClaims() {
        List<Claim> claimList = claimDao.getAllClaims();
        List<ClaimDto> claimDtoList = claimList.stream().map(item -> {
            return claimToClaimDto(item);
        }).collect(Collectors.toList());
        ResponseClaimList responseClaimList = new ResponseClaimList(claimDtoList);
        return gson.toJson(responseClaimList);
    }

    /**
     * Метод, который добавляет claim
     * @param data принимает строковое представление claim
     * @return возвращает claimDto
     */
    public String addClaim(String data) {
        RequestAddClaim requestAddClaim = gson.fromJson(data, RequestAddClaim.class);
        if (requestAddClaim.getDescription() == null || requestAddClaim.getUser_id() == null || requestAddClaim.getProductId() == null || requestAddClaim.getProductId().size() == 0) {
            return gson.toJson(new ResponseAddClaim("невозможно добавить claim, неверный формат сообщения"));
        }
        Claim claim = new Claim();
        claim.setDescription(requestAddClaim.getDescription());
        claim.setUser_id(requestAddClaim.getUser_id());
        claim.setProductId(requestAddClaim.getProductId());
        Claim addedClaim = claimDao.insertClaim(claim);
        ClaimDto claimDto = claimToClaimDto(addedClaim);
        return gson.toJson(new ResponseAddClaim(claimDto));
    }

    /**
     * Метод, который удаляет claim по id
     * @param id claim
     * @return строкое представление ответа success (true/false)
     */
    public String deleteClaim(int id) {
        if (id <= 0) {
            return gson.toJson(new ResponseDeleteClaim("невозможно удалить claim, неверный формат сообщения"));
        }
        boolean success = claimDao.deleteClaim(id);
        return gson.toJson(new ResponseDeleteClaim(success));
    }

    /**
     * Метод, который обновляет claim
     * @param data принимает строковое представление claim
     * @return возвращает claim DTO
     */
    public String updateClaim(String data) {
        RequestUpdateClaim requestUpdateClaim = gson.fromJson(data, RequestUpdateClaim.class);
        if (requestUpdateClaim.getId() == null || (requestUpdateClaim.getDescription() == null && (requestUpdateClaim.getProductId() == null || requestUpdateClaim.getProductId().size() == 0))) {
            return gson.toJson(new ResponseUpdateClaim("невозможно обновить claim, неверный формат сообщения"));
        }
        Claim claim = new Claim();
        claim.setId(requestUpdateClaim.getId());
        claim.setDescription(requestUpdateClaim.getDescription());
        claim.setProductId(requestUpdateClaim.getProductId());
        claim.setUser_id(requestUpdateClaim.getUser_id());
        Claim updatedClaim = claimDao.updateClaim(claim);
        if (updatedClaim == null) {
            return gson.toJson(new ResponseUpdateClaim("невозможно обновить claim, неверный формат сообщения"));
        }
        ClaimDto claimDto = claimToClaimDto(updatedClaim);
        return gson.toJson(new ResponseUpdateClaim(claimDto));
    }

    /**
     * Метод для мэппинга claim в DTO
     * @param item
     * @return claimDto
     */
    private ClaimDto claimToClaimDto(Claim item) {
        if (item == null) {
            return null;
        }
        ClaimDto claimDto = mapperClaim.entityToDto(item);
        claimDto.setUser(mapperUser.entityToDto(userDao.getUserById(item.getUser_id())));
        List<ProductDto> productDtoList = item.getProductId().stream().map(productId -> {
            Product product = productDao.getProductById(productId);
            return mapperProduct.entityToDto(product);
        }).collect(Collectors.toList());
        claimDto.setProductList(productDtoList);
        return claimDto;
    }
}
