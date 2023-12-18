package com.ecommerce.mapper;

import com.ecommerce.model.vo.Res2History;
import com.ecommerce.model.vo.SimpleProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HistoryMapper {


    @Select("insert into `t_history`(buyer_id,product_id,time) values(#{phoneNumber},#{id},#{now})")
    void add(String phoneNumber, Integer id, LocalDateTime now);

    List<Res2History> getList(String buyerId);
}
