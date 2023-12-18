package com.ecommerce.controller;

import com.ecommerce.annotation.GetAttribute;
import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.entity.Address;
import com.ecommerce.model.vo.Result;
import com.ecommerce.service.AddressService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/common/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 添加地址
     * @param address
     * @param phoneNumber
     * @type type
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Address address,
                      @GetAttribute("phoneNumber") String phoneNumber,
                      @GetAttribute("type") String type){
        log.info("添加地址：{},{},{}",address,phoneNumber,type);
        address.setUserId(phoneNumber);

        int code = addressService.add(address);
        return Result.success(address);
    }

    /**
     * 批量删除地址
     * @param list
     * @param phoneNumber
     * @type type
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestBody List<Integer> list,
                         @GetAttribute("phoneNumber") String phoneNumber,
                         @GetAttribute("type") String type){
        log.info("删除地址：{},{},{}",list,phoneNumber,type);
        int code = addressService.delete(list,phoneNumber);
        return Result.success();
    }

    /**
     * 更新地址
     * @param address
     * @param phoneNumber
     * @type type
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Address address,
                         @GetAttribute("phoneNumber") String phoneNumber,
                         @GetAttribute("type") String type){
        log.info("更新地址：{},{},{}",address,phoneNumber,type);
        address.setUserId(phoneNumber);

        int code = addressService.update(address);

        if(code == -1) return Result.error(ResultEnum.NO_FOUND);
        return Result.success();
    }

    /**
     * 获取地址列表
     * @param phoneNumber
     * @type type
     * @return
     */
    @GetMapping("/get")
    public Result getList(@GetAttribute("phoneNumber") String phoneNumber,
                          @GetAttribute("type") String type){
        log.info("获取地址列表：{},{}",phoneNumber,type);
        List<Address> list = addressService.getList(phoneNumber);
        return Result.success(list);
    }
}
