package com.ecommerce.controller;

import com.ecommerce.annotation.GetAttribute;
import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.entity.Order;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.ShoppingCart;
import com.ecommerce.model.vo.*;
import com.ecommerce.service.HistoryService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/foreground/")
public class ForegroundController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private OrderService orderService;

    /**
     * @Description 获取商品列表
     * @param category
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/product/get")
    public Result getProductList(@RequestParam(defaultValue = "") String category,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("获取商品列表，参数：{}，{}，{}",category,page,pageSize);
        PageBean pageBean = productService.getList(null,category,page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * @Description 获取商品详情
     * @param id
     * @return
     */
    @GetMapping("/product/detail")
    public Result getProductDetail(@RequestParam Integer id,
                                   @GetAttribute("phoneNumber") String phoneNumber,
                                   @GetAttribute("type") String type){
        log.info("获取商品详情，参数：{}",id);
        Product product = productService.getDetail(id);
        if(product == null) return Result.error(ResultEnum.NO_FOUND);
        if (type.equals("0")) {
            int code = historyService.add(phoneNumber, id);
        }
        return Result.success(product);
    }

    /**
     * @Description: 添加商品到购物车
     * @Param: phoneNumber
     * @Param: ShoppingCart
     * @return:
     */
    @PostMapping("/shoppingCart/add")
    public Result addProductToShoppingCart(
            @RequestBody ShoppingCart shoppingCart,
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("添加商品到购物车，参数：{}，{}", shoppingCart, phoneNumber);
        shoppingCart.setBuyerId(phoneNumber);

        int code = shoppingCartService.add(shoppingCart);
        if (code == -1) return Result.error(ResultEnum.UNKNOWN_ERROR);
        return Result.success(shoppingCart);
    }

    /**
     * @Description: 批量删除购物车中的商品
     * @param list
     * @param phoneNumber
     * @return
     */
    @DeleteMapping("/shoppingCart/delete")
    public Result deleteProductsFromShoppingCart(
            @RequestBody List<Integer> list,
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("从购物车删除商品，参数：{}，{}", list, phoneNumber);
        int code = shoppingCartService.delete(list, phoneNumber);
        return Result.success();
    }

    /**
     * @Description: 更新购物车中的商品
     * @param shoppingCart
     * @param phoneNumber
     * @return
     */
    @PutMapping("/shoppingCart/update")
    public Result updateProductsFromShoppingCart(
            @RequestBody ShoppingCart shoppingCart,
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("更新购物车中的商品，参数：{}，{}", shoppingCart, phoneNumber);
        shoppingCart.setBuyerId(phoneNumber);
        int code = shoppingCartService.update(shoppingCart);
        if (code == -1) return Result.error(ResultEnum.NO_FOUND);
        return Result.success();
    }


    /**
     * @Description: 获取购物车中的商品
     * @param phoneNumber
     * @return
     */
    @GetMapping("/shoppingCart/get")
    public Result getProductsFromShoppingCart(
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("获取购物车中的商品，参数：{}", phoneNumber);
        List<ShoppingCartProduct> shoppingCartList = shoppingCartService.get(phoneNumber);
        return Result.success(shoppingCartList);
    }

    /**
     * @Description: 生成订单
     * @param phoneNumber
     * @param list
     * @return
     */
    @PostMapping("/order/addList")
    public Result addOrderList(
            @GetAttribute("phoneNumber") String phoneNumber,
            @RequestBody List<Order> list) {
        log.info("生成订单，参数：{}，{}", phoneNumber, list);
        if(list==null){
            return Result.error(ResultEnum.NO_FOUND);
        }
        int code = orderService.add(phoneNumber, list);

        if (code == -1) return Result.error(ResultEnum.NO_FOUND);
        else if (code == -2) return Result.error(ResultEnum.NO_FOUND);
        else if (code == -3) return Result.error(ResultEnum.NOT_ENOUGH);
        else if (code == -4) return Result.error(ResultEnum.ADDRESS_ERROR);
        return Result.success();
    }

    /**
     * @Description: 查询订单
     * @param orderId
     * @param phoneNumber
     * @return
     */
    @GetMapping("/order/getById")
    public Result getOrderById(
            @RequestParam Integer orderId,
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("查询订单，参数：{}，{}", orderId, phoneNumber);

        Order order = orderService.getById(orderId, phoneNumber);
        if (order == null) return Result.error(ResultEnum.NO_FOUND);
        return Result.success(order);
    }

    /**
     * @Description: 查询订单列表
     * @param phoneNumber
     * @return
     */
    @GetMapping("/order/getList")
    public Result getOrderList(
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("查询订单，参数：{}", phoneNumber);

        List<Res2Order> list = orderService.getList(phoneNumber);
        return Result.success(list);
    }

    /**
     * @Description: 历史浏览记录
     * @param phoneNumber
     * @return
     */
    @GetMapping("/history/getList")
    public Result getHistoryList(
            @GetAttribute("phoneNumber") String phoneNumber) {
        log.info("历史浏览记录，参数：{}", phoneNumber);

        List<Res2History> productList = historyService.getList(phoneNumber);
        return Result.success(productList);
    }


}
