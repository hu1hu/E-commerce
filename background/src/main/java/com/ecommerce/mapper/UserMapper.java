package com.ecommerce.mapper;

import com.ecommerce.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据电话号码与类型查询记录
     * @param phoneNumber
     * @param type
     * @return
     */
    @Select("select * from `t_user` where `phone_number`=#{phoneNumber} and `type`=#{type}")
    User selectByPhoneNumberAndType(String phoneNumber, String type);

    /**
     * 插入数据
     * @param user
     */
    @Insert("insert into `t_user` values(#{phoneNumber},#{email},#{userName},#{password},#{gender},#{type})")
    void insert(User user);

}
