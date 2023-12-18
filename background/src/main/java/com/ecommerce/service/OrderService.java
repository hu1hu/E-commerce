package com.ecommerce.service;

import com.ecommerce.model.entity.Order;
import com.ecommerce.model.vo.PageBean;
import com.ecommerce.model.vo.Res2Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface OrderService {

    /**
     * @description: 添加订单
     * @param phoneNumber
     * @param list
     * @return 成功返回0，商品不存在返回-1，商品已下架返回-2，商品库存不足返回-3，地址不存在返回-4
     */
    int add(String phoneNumber, List<Order> list);

    Order getById(Integer orderId, String phoneNumber);

    PageBean getSaleList(String phoneNumber, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    List<Res2Order> getList(String phoneNumber);
}
