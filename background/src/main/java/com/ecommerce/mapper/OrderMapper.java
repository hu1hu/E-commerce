package com.ecommerce.mapper;

import com.ecommerce.model.entity.Order;
import com.ecommerce.model.vo.Res2Order;
import com.ecommerce.model.vo.SalesData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true,keyProperty = "orderId")
    @Insert("insert into `t_order`(buyer_id,product_id,address,price,time,count,state,receiver,phone_number)" +
            "values(#{buyerId},#{productId},#{address},#{price},#{time},#{count},#{state},#{receiver},#{phoneNumber}) ")
    void add(Order order);

    @Select("select * from `t_order` where order_id = #{orderId} and buyer_id = #{phoneNumber}")
    Order getById(Integer orderId, String phoneNumber);

    List<SalesData> getSaleList(String phoneNumber, LocalDate begin, LocalDate end);

    List<Res2Order> getList(String phoneNumber);
}
