package com.ecommerce.service;


import com.ecommerce.model.entity.ShoppingCart;
import com.ecommerce.model.vo.ShoppingCartProduct;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加商品到购物车
     * @param shoppingCart
     * @return 0：成功，-1：失败
     */
    int add(ShoppingCart shoppingCart);

    int delete(List<Integer> list, String uid);

    List<ShoppingCartProduct> get(String phoneNumber);

    /**
     * 更新购物车中的商品
     * @param shoppingCart
     * @return 0：成功，-1：失败
     */
    int update(ShoppingCart shoppingCart);
}
