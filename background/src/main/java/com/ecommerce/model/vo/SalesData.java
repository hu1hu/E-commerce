package com.ecommerce.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesData {
    private Integer id;
    private String name;
    private Integer totalNumber;
    private Double totalMoney;
}
