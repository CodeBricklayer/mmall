package com.tanp.mmall.controller.backend;

import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author PangT
 * @since 2018/12/20
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

  @Autowired
  private IUserService iUserService;

  /**
   * 管理员登录
   *
   * @param username 用户名
   * @param password 密码
   * @param session 会话对象
   * @return 返回登录结果
   */
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse<User> login(String username, String password, HttpSession session) {
    ServerResponse<User> response = iUserService.login(username, password);
    if (response.isSuccess()) {
      User user = response.getData();
      if (user.getRole() == Const.Role.ROLE_ADMIN) {
        //说明登录的是管理员
        session.setAttribute(Const.CURRENT_USER, user);
        return response;
      } else {
        return ServerResponse.createByErrorMessage("不是管理员，无法登录");
      }
    }
    return response;
  }
}
