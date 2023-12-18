package com.ecommerce.service;

import com.ecommerce.model.entity.User;

public interface AccountService {

    /**
     * 登录
     * @param user
     * @return 成功：0，未注册：-1，密码错误：-2
     */
    int login(User user);

    /**
     * 注册
     * @param user
     * @return 成功：0，已注册：-1
     */
    int register(User user);

    User getInfo(String phoneNumber, String type);
}
