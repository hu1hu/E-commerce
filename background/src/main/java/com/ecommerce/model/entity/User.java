package com.ecommerce.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String phoneNumber;
    private String email;
    private String userName;
    private String password;
    private String gender;
    private String type;
}
