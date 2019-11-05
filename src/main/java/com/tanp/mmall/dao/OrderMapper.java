package com.tanp.mmall.dao;

import com.tanp.mmall.pojo.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Order record);

  int insertSelective(Order record);

  Order selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Order record);

  int updateByPrimaryKey(Order record);

  //根据订单号和用户的Id来查询订单
  Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);
}