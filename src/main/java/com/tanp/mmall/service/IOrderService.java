package com.tanp.mmall.service;

import com.github.pagehelper.PageInfo;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.vo.OrderVo;
import java.util.Map;

/**
 * @author CodeBricklayer
 * @date 2019/11/5 12:01
 * @description TODO
 */
public interface IOrderService {

  /**
   * @param orderNo 订单号
   * @param userId 用户Id
   * @param path 支付码路径
   * @description 支付接口的实现
   */
  ServerResponse pay(Long orderNo, Integer userId, String path);

  /**
   * @param params 参数
   * @description 支付宝的回调通知接口
   */
  ServerResponse aliCallBack(Map<String, String> params);

  /**
   * @param userId 用户ID
   * @param orderNo 订单号
   * @description 查询订单状态的接口实现
   */
  ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);


  /**
   * @param userId 用户ID
   * @param shippingId 商品ID
   * @description 创建订单接口的实现
   */
  ServerResponse createOrder(Integer userId, Integer shippingId);

  /**
   * @param userId 用户ID
   * @param orderNo 订单编号
   * @description 取消订单接口的开发
   */
  ServerResponse<String> cancel(Integer userId, Long orderNo);

  /**
   * @param userId 用户ID
   * @description 获取购物车接口 (已经选中打算结算的购物车中商品的信息接口)
   */
  ServerResponse getOrderCartProduct(Integer userId);


  /**
   * @param userId 用户ID
   * @param orderNo 订单编号
   * @description 查询订单详情的接口实现
   */
  ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);


  /**
   * @param userId 用户ID
   * @param pageNum 页面
   * @param pageSize 数量
   * @description 个人中心查看订单列表接口
   */
  ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);


  /**
   * @param pageNum 页面
   * @param pageSize 数量
   * @description backed 管理员层的相关接口实现方法
   */
  ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

  /**
   * @param orderNo 订单编号
   * @description 后台管理员查看订单详情接口实现
   */
  ServerResponse<OrderVo> manageDetail(Long orderNo);

  /**
   * @param orderNo 订单编号
   * @param pageNum 页面
   * @param pageSize 数量
   * @description 后台管理员订单搜索接口实现 (暂时是精确匹配)
   */
  ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

  /**
   * @param orderNo 订单编号
   * @description 管理员根据订单号发货接口
   */
  ServerResponse<String> manageSendGoods(Long orderNo);

}
