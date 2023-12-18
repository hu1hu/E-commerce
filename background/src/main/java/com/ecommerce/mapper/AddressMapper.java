package com.ecommerce.mapper;

import com.ecommerce.model.entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id") // /会自动将生成的主键值，赋值给address对象的id属性
    @Insert("insert into `t_address`(user_id,receiver,address,phone_number) values(#{userId},#{receiver},#{address},#{phoneNumber})")
    void add(Address address);

    int deleteList(List<Integer> list,String uid);

    @Select("select * from `t_address` where user_id=#{uid}")
    List<Address> getList(String uid);

    @Select("select * from `t_address` where id=#{id}")
    Address getById(Integer id);

    int update(Address address);
}
