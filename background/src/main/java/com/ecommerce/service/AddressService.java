package com.ecommerce.service;

import com.ecommerce.model.entity.Address;

import java.util.List;

public interface AddressService {
    /**
     * 添加地址
     * @param address
     * @return
     */
    int add(Address address);

    /**
     * 更新地址
     * @param address
     * @return 成功：0，id错误：
     */
    int update(Address address);

    /**
     * 删除地址
     * @param list
     * @return
     */
    int delete(List<Integer> list,String uid);


    List<Address> getList(String uid);
}
