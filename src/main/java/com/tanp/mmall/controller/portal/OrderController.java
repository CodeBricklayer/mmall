package com.tanp.mmall.controller.portal;

import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.IOrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author CodeBricklayer
 * @date 2019/11/5 11:54
 * @description TODO
 */
@Controller
@RequestMapping("/order")
public class OrderController {

  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private IOrderService iOrderService;

  @RequestMapping("/pay.do")
  @ResponseBody
  public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
          ResponseCode.NEED_LOGIN.getDesc());
    }
    //这是一个文件目录，用户存放支付宝中生成的二维码存放目录，对应的目录路径是tomcat 服务器目录
    String path = request.getSession().getServletContext().getRealPath("upload");

    return iOrderService.pay(orderNo, user.getId(), path);
  }
}
