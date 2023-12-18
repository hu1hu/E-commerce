package com.ecommerce.model.vo;

import com.ecommerce.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res2Login {
    private String token;
    private String phoneNumber;
    private String email;
    private String userName;
    private String gender;
    private String type;
}
