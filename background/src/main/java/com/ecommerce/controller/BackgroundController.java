package com.ecommerce.controller;

import com.ecommerce.annotation.GetAttribute;
import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.vo.PageBean;
import com.ecommerce.model.vo.Result;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/background")
public class BackgroundController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    /**
     * 添加商品
     * @param product
     * @param phoneNumber
     * @return
     */
    @PostMapping("/product/add")
    public Result add(@RequestBody Product product,
                      @GetAttribute("phoneNumber") String phoneNumber){
        log.info("添加商品：{},{}",product,phoneNumber);
        product.setSellerId(phoneNumber);
        int code = productService.add(product);
        return Result.success(product);
    }

    /**
     * 批量删除商品
     * @param list
     * @param phoneNumber
     * @return
     */
    @DeleteMapping("/product/delete")
    public Result deleteList(@RequestBody List<Integer> list,
                             @GetAttribute("phoneNumber") String phoneNumber){
        log.info("批量删除商品：{},{}",list,phoneNumber);
        int code = productService.deleteList(list,phoneNumber);
        return Result.success();
    }

    /**
     * 更新商品
     * @param product
     * @param phoneNumber
     * @return
     */
    @PutMapping("/product/update")
    public Result update(@RequestBody Product product,
                         @GetAttribute("phoneNumber") String phoneNumber){
        log.info("更新商品{},{}",product,phoneNumber);
        product.setSellerId(phoneNumber);

        int code = productService.update(product);

        if(code == -1) return Result.error(ResultEnum.NO_FOUND);
        return Result.success();
    }

    /**
     * 根据uid查询商品列表
     * @param page
     * @param pageSize
     * @param phoneNumber
     * @return
     */
    @GetMapping("/product/get")
    public Result getList(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @GetAttribute("phoneNumber") String phoneNumber){
        log.info("获取商品列表：{}，{}，{}",page,pageSize,phoneNumber);
        PageBean pageBean = productService.getList(phoneNumber,null,page,pageSize);

        return Result.success(pageBean);
    }

    /**
     * 根据uid查询商品列表
     * @param page
     * @param pageSize
     * @param phoneNumber
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/saleProduct/getList")
    public Result getSaleList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @GetAttribute("phoneNumber") String phoneNumber,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("获取商品列表：{}，{}，{}，{}，{}",page,pageSize,phoneNumber,begin,end);
        PageBean pageBean = orderService.getSaleList(phoneNumber,begin,end,page,pageSize);
        return Result.success(pageBean);
    }



}
