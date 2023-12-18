package com.ecommerce.mapper;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.vo.SimpleProduct;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id") // /会自动将生成的主键值，赋值给Product对象的id属性
    @Insert("insert into `t_product` (name,category,price,description,seller_id,cover_url,count) " +
            "values (#{name},#{category},#{price},#{description},#{sellerId},#{coverUrl},#{count})")
    void add(Product product);

    void deleteList(List<Integer> list);


    int update(Product product);


    List<Product> getList(String uid, String category);

    @Select("select * from `t_product` where id = #{id}")
    Product getDetail(Integer id);

}

