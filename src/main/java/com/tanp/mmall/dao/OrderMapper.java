package com.tanp.mmall.dao;

import com.tanp.mmall.pojo.Order;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Order record);

  int insertSelective(Order record);

  Order selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Order record);

  int updateByPrimaryKey(Order record);

  /**
   * @param orderNo 订单号
   * @param userId 用户ID
   * @description 根据用户的Id和订单号获取订单详情
   */
  Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);


  /**
   * @param orderNo 订单号
   * @description 根据订单号来查询订单是否存在
   */
  Order selectByOrderNo(Long orderNo);


  /**
   * @param userId 用户ID
   * @return 用户订单列表
   * @description 根据用户Id来查询订单列表
   */
  List<Order> getOrderListByUserId(Integer userId);

  /**
   * @return 订单列表
   * @description 管理员查看订单列表实现
   */
  List<Order> selectAllOrder();
}