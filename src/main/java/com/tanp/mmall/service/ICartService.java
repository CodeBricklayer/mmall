package com.tanp.mmall.service;

import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.vo.CartVo;

public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
}
