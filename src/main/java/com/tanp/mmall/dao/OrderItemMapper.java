package com.tanp.mmall.dao;

import com.tanp.mmall.pojo.OrderItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderItemMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(OrderItem record);

  int insertSelective(OrderItem record);

  OrderItem selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(OrderItem record);

  int updateByPrimaryKey(OrderItem record);

  //根据用户的Id和订单号获取订单详情
  List<OrderItem> getByOrderNoUserId(@Param("orderNo") Long orderNo,
      @Param("userId") Integer userId);
}