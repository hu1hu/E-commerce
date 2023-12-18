package com.ecommerce.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res2History {
    private Integer id;
    private String name;
    private Double price;
    private String coverUrl;
    private LocalDateTime time;
}
