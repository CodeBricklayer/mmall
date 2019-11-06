package com.tanp.mmall.dao;

import com.tanp.mmall.pojo.Order;
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

  /**
   * 根据用户的Id和订单号获取订单详情
   *
   * @param orderNo 订单号
   * @param userId 用户ID
   * @return 订单详情
   */
  List<OrderItem> getByOrderNoUserId(@Param("orderNo") Long orderNo,
      @Param("userId") Integer userId);


  /**
   * 根据订单号获取订单详情(管理员查询)
   *
   * @param orderNo 订单号
   * @return 订单详情
   */
  List<OrderItem> getAllByOrderNo(Long orderNo);


  /**
   * 批量插入订单中的商品信息
   *
   * @param orderItemList 订单商品信息
   */
  void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);

}