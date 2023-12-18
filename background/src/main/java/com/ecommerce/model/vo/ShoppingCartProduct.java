package com.ecommerce.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartProduct {
    private String id;
    private String buyerId;
    private Integer count;

    private Integer productId;
    private String productName;
    private Double productPrice;
    private String productCoverUrl;
}
