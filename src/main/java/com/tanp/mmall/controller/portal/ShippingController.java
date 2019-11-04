package com.tanp.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.Shipping;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 收货地址增，删，改，查，分页列表，地址详情功能开发
 *
 * @author PangT
 * @since 2019/1/10
 */
@RestController
@RequestMapping("/shipping")
public class ShippingController {

  @Autowired
  private IShippingService iShippingService;

  /**
   * 新增收货地址
   *
   * @param session 会话对象
   * @param shipping 地址信息
   * @return 返回结果
   */
  @RequestMapping(value = "/add.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse add(HttpSession session, Shipping shipping) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
          ResponseCode.NEED_LOGIN.getDesc());
    }
    return iShippingService.add(user.getId(), shipping);
  }

  /**
   * 删除收货地址
   *
   * @param session 会话对象
   * @param shippingId 地址id
   * @return 返回结果
   */
  @RequestMapping(value = "/del.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse del(HttpSession session, Integer shippingId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
          ResponseCode.NEED_LOGIN.getDesc());
    }
    return iShippingService.del(user.getId(), shippingId);
  }

  /**
   * 更新收货地址
   *
   * @param session 会话对象
   * @param shipping 地址信息
   * @return 返回结果
   */
  @RequestMapping(value = "/update.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse update(HttpSession session, Shipping shipping) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
          ResponseCode.NEED_LOGIN.getDesc());
    }
    return iShippingService.update(user.getId(), shipping);
  }

  /**
   * 查询地址信息
   *
   * @param session 会话对象
   * @param shippingId 地址id
   * @return 返回结果
   */
  @RequestMapping(value = "/select.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<Shipping> select(HttpSession session, Integer shippingId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
          ResponseCode.NEED_LOGIN.getDesc());
    }
    return iShippingService.select(user.getId(), shippingId);
  }

  /**
   * 返回地址列表
   *
   * @param pageNum 页码
   * @param pageSize 页面数量
   * @param session 会话对象
   * @return 返回结果
   */
  @RequestMapping(value = "/list.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<PageInfo> list(
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      HttpSession session) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
          ResponseCode.NEED_LOGIN.getDesc());
    }
    return iShippingService.list(user.getId(), pageNum, pageSize);
  }
}
