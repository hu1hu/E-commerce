package com.ecommerce.service.impl;

import com.ecommerce.mapper.AddressMapper;
import com.ecommerce.model.entity.Address;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public int add(Address address) {
        addressMapper.add(address);
        return 0;
    }

    @Override
    public int update(Address address) {
        int count = addressMapper.update(address);
        if(count == 1) return 0;
        return -1;
    }

    @Override
    @Transactional
    public int delete(List<Integer> list, String uid) {
        int count = addressMapper.deleteList(list,uid);
        return 0;
    }

    @Override
    public List<Address> getList(String uid) {
        List<Address> list = addressMapper.getList(uid);
        return list;
    }
}
