package com.tanp.mmall.controller.portal;

import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.ICartService;
import com.tanp.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 购物车功能
 *
 * @author PangT
 * @since 2019/1/4
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService iCartService;

    /**
     * 购物车商品添加
     *
     * @param session   会话对象
     * @param count     数量
     * @param productId 商品id
     * @return 返回结果
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, count);
    }

}
