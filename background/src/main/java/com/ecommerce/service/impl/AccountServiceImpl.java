package com.ecommerce.service.impl;

import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.entity.User;
import com.ecommerce.service.AccountService;
import com.ecommerce.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int login(User user){
        User user_temp = userMapper.selectByPhoneNumberAndType(user.getPhoneNumber(),user.getType());
        if(user_temp == null) return -1;
        if(user_temp.getPassword() == null) return -2;
        boolean b = PasswordUtil.verifyPassword(user.getPassword(),user_temp.getPassword());
        if(b){
            user.setUserName(user_temp.getUserName());
            user.setEmail(user_temp.getEmail());
            user.setGender(user_temp.getGender());
            return 0;
        }
        return -2;
    }

    @Transactional // 开启事务
    @Override
    public int register(User user) {
        // 查询有无注册
        User user_temp = userMapper.selectByPhoneNumberAndType(user.getPhoneNumber(),user.getType());

        if(user_temp==null){
            // 加密
            user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
            userMapper.insert(user);
            return 0;
        }else{
            return -1;
        }
    }

    @Override
    public User getInfo(String phoneNumber, String type) {
        User user = userMapper.selectByPhoneNumberAndType(phoneNumber,type);
        user.setPassword(null);
        return user;
    }
}
