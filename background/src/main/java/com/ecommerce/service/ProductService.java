package com.ecommerce.service;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.vo.PageBean;

import java.util.List;

public interface ProductService {
    /**
     * 添加商品
     * @param product
     * @return
     */
    int add(Product product);

    /**
     * 删除商品
     * @param list
     * @param uid
     * @return
     */
    int deleteList(List<Integer> list,String uid);

    /**
     * 更新商品
     * @param product
     * @return 成功：0，id失效-1
     */
    int update(Product product);

    PageBean getList(String uid,String category, Integer page, Integer pageSize);

    Product getDetail(Integer id);
}
