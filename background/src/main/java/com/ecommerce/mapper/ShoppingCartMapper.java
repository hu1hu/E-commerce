package com.ecommerce.mapper;

import com.ecommerce.model.vo.ShoppingCartProduct;
import org.apache.ibatis.annotations.*;
import com.ecommerce.model.entity.ShoppingCart;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id") // /会自动将生成的主键值，赋值给shoppingCart对象的id属性
    @Insert("insert into `t_shopping_cart` (buyer_id, product_id, count) " +
            "values (#{buyerId}, #{productId}, #{count})")
    int insert(ShoppingCart shoppingCart);

    int delete(List<Integer> list, String uid);

    List<ShoppingCartProduct> selectByUid(String uid);

    @Update("update `t_shopping_cart` set count = #{count} where id = #{id}")
    int update(ShoppingCart shoppingCart);
}
