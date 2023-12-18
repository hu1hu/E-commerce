package com.ecommerce.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer orderId;
    private String buyerId;
    private Integer productId;
    private String address;
    private String receiver;
    private String phoneNumber;
    private Double price;
    private LocalDateTime time;
    private Integer count;
    private String state;
    private Integer addressId;
}
