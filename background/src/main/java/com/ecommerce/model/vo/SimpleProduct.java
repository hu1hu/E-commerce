package com.ecommerce.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleProduct {
    private Integer id;
    private String name;
    private Double price;
    private String coverUrl;
}
