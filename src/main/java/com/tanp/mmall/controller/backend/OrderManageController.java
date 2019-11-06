package com.tanp.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.IOrderService;
import com.tanp.mmall.service.IUserService;
import com.tanp.mmall.vo.OrderVo;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author CodeBricklayer
 * @date 2019/11/6 12:03
 * @description TODO
 */

@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

  @Autowired
  private IUserService iUserService;

  @Autowired
  private IOrderService iOrderService;

  //管理员查看订单列表接口
  @RequestMapping("/orderList.do")
  @ResponseBody
  public ServerResponse<PageInfo> orderList(HttpSession session,
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    //验证用户是否登录
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，请先登录");
    }
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //是管理员，增加我们处理分类的逻辑
      return iOrderService.manageList(pageNum, pageSize);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }


  //管理员查看订单列表接口
  @RequestMapping("/orderDetail.do")
  @ResponseBody
  public ServerResponse<OrderVo> orderDetail(HttpSession session, Long orderNo) {
    //验证用户是否登录
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
    }
    //校验是否是管理员
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //是管理员，增加我们处理分类的逻辑
      return iOrderService.manageDetail(orderNo);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }

  //管理员根据订单号搜索
  @RequestMapping("/orderSearch.do")
  @ResponseBody
  public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo,
      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    //验证用户是否登录
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
    }
    //校验是否是管理员
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //是管理员，增加我们处理分类的逻辑
      return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }

  //管理员根据订单号发货接口
  @RequestMapping("/sendGoods.do")
  @ResponseBody
  public ServerResponse<String> sendGoods(HttpSession session, Long orderNo) {
    //验证用户是否登录
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
    }
    //校验是否是管理员
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //是管理员，增加我们处理分类的逻辑
      return iOrderService.manageSendGoods(orderNo);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }
}
