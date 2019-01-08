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

    /**
     * 更新购物车商品
     *
     * @param session   会话对象
     * @param count     数量
     * @param productId 商品id
     * @return 返回结果
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    /**
     * 删除购物车商品
     *
     * @param session    会话对象
     * @param productIds 商品id
     * @return 返回结果
     */
    @RequestMapping(value = "/deleteProduct.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    /**
     * 购物车全选
     *
     * @param session 会话对象
     * @return 返回结果
     */
    @RequestMapping(value = "/selectAll.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    /**
     * 购物车全部反选
     *
     * @param session 会话对象
     * @return 返回结果
     */
    @RequestMapping(value = "/unSelectAll.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    /**
     * 购物车单选
     *
     * @param session   会话对象
     * @param productId 商品id
     * @return 返回结果
     */
    @RequestMapping(value = "/select.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    /**
     * 购物车单反选
     *
     * @param session   会话对象
     * @param productId 商品id
     * @return 返回结果
     */
    @RequestMapping(value = "/unSelect.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    /**
     * 查询当前用户的购物车的商品数量，如果一个商品有10个，那么数量就是10，不按商品种类算，
     * 按数量计算，这是电商的通用做法
     *
     * @param session 会话对象
     * @return 返回结果
     */
    @RequestMapping(value = "/getCartProductCount.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.getCartProductCount(user.getId());
    }
}
