package com.tanp.mmall.service;

import com.tanp.mmall.common.ServerResponse;

/**
 * @author CodeBricklayer
 * @date 2019/11/5 12:01
 * @description TODO
 */
public interface IOrderService {

  //支付接口的实现
  ServerResponse pay(Long orderNo, Integer userId, String path);
}
