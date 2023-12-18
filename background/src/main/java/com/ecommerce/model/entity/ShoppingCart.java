package com.ecommerce.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    private String id;
    private String buyerId;
    private Integer productId;
    private Integer count;
}
