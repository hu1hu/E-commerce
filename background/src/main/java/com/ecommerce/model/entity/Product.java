package com.ecommerce.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String category;
    private String name;
    private Double price;
    private String description;
    private String sellerId;
    private String coverUrl;
    private Integer count;
}
