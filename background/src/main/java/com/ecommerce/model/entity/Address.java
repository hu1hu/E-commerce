package com.ecommerce.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Integer id;
    private String userId;
    private String receiver;
    private String address;
    private String phoneNumber;
}
