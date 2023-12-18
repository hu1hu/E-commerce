package com.ecommerce.controller;

import com.ecommerce.annotation.GetAttribute;
import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.entity.User;
import com.ecommerce.model.vo.Res2Login;
import com.ecommerce.model.vo.Result;
import com.ecommerce.service.AccountService;
import com.ecommerce.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/common/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("登录：{},{}",user.getPhoneNumber(),user.getType());
        int code = accountService.login(user);

        if(code == -1) return Result.error(ResultEnum.UNREGISTERED);
        if(code == -2) return Result.error(ResultEnum.INVALID_PASSWORD);
        // 登录成功，生成令牌，下发令牌
        if(code == 0){
            Map<String, Object> claims = new HashMap<>();
            claims.put("phoneNumber",user.getPhoneNumber());
            claims.put("type",user.getType());
            String jwt = JwtUtils.generateJwt(claims);
            user.setPassword(null);
            return Result.success(new Res2Login(
                    jwt,
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getUserName(),
                    user.getGender(),
                    user.getType()
            ));
        }
        return Result.error(ResultEnum.UNKNOWN_ERROR);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("注册：{}",user);
        int code = accountService.register(user);

        if(code == -1) return Result.error(ResultEnum.ACCOUNT_ALREADY_EXISTS);
        // 注册成功，生成令牌，下发令牌
        if(code == 0){
            Map<String, Object> claims = new HashMap<>();
            claims.put("phoneNumber",user.getPhoneNumber());
            claims.put("type",user.getType());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error(ResultEnum.UNKNOWN_ERROR);
    }

    /**
     * 获取个人信息
     * @param phoneNumber
     * @param type
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo(@GetAttribute("phoneNumber") String phoneNumber,
                          @GetAttribute("type") String type){
        log.info("获取个人信息：{},{}",phoneNumber,type);
        User user = accountService.getInfo(phoneNumber,type);
        if(user == null) return Result.error(ResultEnum.UNKNOWN_ERROR);
        return Result.success(user);
    }
}
