package com.ecommerce.service.impl;


import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.vo.PageBean;
import com.ecommerce.model.vo.SimpleProduct;
import com.ecommerce.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public int add(Product product) {
        productMapper.add(product);
        return 0;
    }

    @Override
    @Transactional
    public int deleteList(List<Integer> list,String uid) {
        Product product = new Product(0,null,null,null,null,"none",null,0);
        for (Integer integer : list) {
            product.setId(integer);
            productMapper.update(product);
        }
        return 0;
    }

    @Override
    public int update(Product product) {
        int count = productMapper.update(product);
        if(count == 1) return 0;
        return -1;
    }

    @Override
    public PageBean getList(String uid,String category,Integer page, Integer pageSize) {
        // 1.设置分页参数
        PageHelper.startPage(page,pageSize);
        // 2.执行查询
        Page<Product> p = (Page<Product>) productMapper.getList(uid,category);
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public Product getDetail(Integer id) {
        return productMapper.getDetail(id);
    }


}
