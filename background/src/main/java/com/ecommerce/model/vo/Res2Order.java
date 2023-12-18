package com.ecommerce.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res2Order {
    private Integer orderId;
    private String buyerId;
    private Integer productId;
    private String productName;
    private String productCoverUrl;
    private String address;
    private String receiver;
    private String phoneNumber;
    private Double price;
    private LocalDateTime time;
    private Integer count;
    private String state;
    private Integer addressId;
}
