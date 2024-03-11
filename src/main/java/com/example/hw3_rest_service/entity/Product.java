package com.example.hw3_rest_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Anna Belousova
 * Класс для создания сущности Product
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private int weight;
    private List<Integer> claimList;
}
