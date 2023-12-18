package com.ecommerce.service.impl;

import com.ecommerce.mapper.ShoppingCartMapper;
import com.ecommerce.model.entity.ShoppingCart;
import com.ecommerce.model.vo.ShoppingCartProduct;
import com.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Override
    public int add(ShoppingCart shoppingCart) {
        int result = shoppingCartMapper.insert(shoppingCart);
        if(result == 0) return -1;
        return 0;
    }

    @Override
    public int delete(List<Integer> list, String uid) {
        int result = shoppingCartMapper.delete(list,uid);
        return 0;
    }

    @Override
    public List<ShoppingCartProduct> get(String uid) {
        return shoppingCartMapper.selectByUid(uid);
    }

    @Override
    public int update(ShoppingCart shoppingCart) {
        int result = shoppingCartMapper.update(shoppingCart);
        if(result == 0) return -1;
        return 0;
    }
}
