package com.tanp.mmall.service;

import com.tanp.mmall.common.ServerResponse;
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

}
