package com.ecommerce.service.impl;


import com.ecommerce.mapper.*;
import com.ecommerce.model.entity.Address;
import com.ecommerce.model.entity.Order;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.User;
import com.ecommerce.model.vo.PageBean;
import com.ecommerce.model.vo.Res2Order;
import com.ecommerce.model.vo.SalesData;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.utils.AliEmailUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AliEmailUtils aliEmailUtils;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public int add(String phoneNumber, List<Order> list) {
        User user = userMapper.selectByPhoneNumberAndType(phoneNumber, "0");
        List<Integer> deleteList = new LinkedList();
        for (Order order : list) {
            order.setBuyerId(phoneNumber);
            order.setState("3");
            order.setTime(LocalDateTime.now());
            // 查询商品信息
            Product product = productMapper.getDetail(order.getProductId());
            if (product == null) return -1;
            else if(product.getSellerId() == "none") return -2;
            else if(product.getCount() < order.getCount()) return -3;
            else {
                order.setPrice(product.getPrice());
                // 更新商品库存
                product.setCount(product.getCount() - order.getCount());
                productMapper.update(product);
            }
            // 查询送货地址
            if (order.getAddressId() == null) return -4;
            Address address = addressMapper.getById(order.getAddressId());
            if (address == null) return -4;
            order.setAddress(address.getAddress());
            order.setPhoneNumber(address.getPhoneNumber());
            order.setReceiver(address.getReceiver());
            // 添加订单
            orderMapper.add(order);
            // 发送确认收货邮件
            try{
                aliEmailUtils.send(
                        aliEmailUtils.buildContent(order.getReceiver(), order.getOrderId().toString(), product.getName(), order.getTime()),
                        user.getEmail()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteList.add(product.getId());
        }
        // 删除购物车中的商品
        shoppingCartMapper.delete(deleteList, phoneNumber);
        return 0;
    }

    @Override
    public Order getById(Integer orderId, String phoneNumber) {
        Order order = orderMapper.getById(orderId, phoneNumber);
        return order;
    }

    @Override
    public PageBean getSaleList(String phoneNumber, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<SalesData> p =(Page<SalesData>) orderMapper.getSaleList(phoneNumber, begin, end);
        return new PageBean(p.getTotal(), p.getResult());
    }

    @Override
    public List<Res2Order> getList(String phoneNumber) {
        return orderMapper.getList(phoneNumber);
    }
}
