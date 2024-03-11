package com.example.hw3_rest_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @author Anna Belousova
 * Класс для создания сущности Claim
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    private int id;
    private String description;
    private int user_id;
    private List<Integer> productId;

    /**
     * Метод для сравнения объектов
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Claim)) return false;
        Claim claim = (Claim) o;
        return user_id == claim.user_id && Objects.equals(description, claim.description) && Objects.equals(productId, claim.productId);
    }

    /**
     * Метод,для нахождения hashcode объекта
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(description, user_id, productId);
    }
}
