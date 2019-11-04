package com.tanp.mmall.controller.portal;

import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
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
 * 横向越权、纵向越权安全漏洞: 横向越权：攻击者尝试访问与他拥有相同权限的用户的资源 纵向越权：低级别攻击者尝试访问高级别用户的资源
 *
 * @author PangT
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private IUserService iUserService;

  /**
   * 用户登陆
   *
   * @param username 用户名
   * @param password 密码
   * @param session 会话对象 Cookie是把用户的数据写给用户的浏览器。 Session技术把用户的数据写到用户独占的session中。
   * Session对象由服务器创建，开发人员可以调用request对象的getSession方法得到session对象。
   * @return 返回json串
   */
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse<User> login(String username, String password, HttpSession session) {
    //service-->mybatis-->dao
    ServerResponse<User> response = iUserService.login(username, password);
    if (response.isSuccess()) {
      session.setAttribute(Const.CURRENT_USER, response.getData());
    }
    return response;
  }


  /**
   * 用户登出
   *
   * @param session 会话对象
   * @return 返回json串
   */
  @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<String> logout(HttpSession session) {
    session.removeAttribute(Const.CURRENT_USER);
    return ServerResponse.createBySuccess();
  }

  /**
   * 用户注册
   *
   * @param user 用户注册信息
   * @return 返回json串
   */
  @RequestMapping(value = "/register.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<String> register(User user) {
    return iUserService.register(user);
  }

  public ServerResponse<String> checkValid(String str, String type) {
    return iUserService.checkValid(str, type);
  }

  /**
   * 获取用户登陆信息
   *
   * @param session 会话对象
   * @return 返回结果
   */
  @RequestMapping(value = "/getUserInfo.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<User> getUserInfo(HttpSession session) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user != null) {
      return ServerResponse.createBySuccess(user);
    }
    return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
  }

  /**
   * 忘记密码
   *
   * @param username 用户名
   * @return 返回答案
   */
  @RequestMapping(value = "/forgetGetQuestion.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<String> forgetGetQuestion(String username) {
    return iUserService.selectQuestion(username);
  }

  /**
   * 检查问题答案
   *
   * @param username 用户名
   * @param question 问题
   * @param answer 答案
   * @return 返回检查结果
   */
  @RequestMapping(value = "forgetCheckAnswer.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
    return iUserService.checkAnswer(username, question, answer);
  }

  /**
   * 忘记密码中的重置密码
   *
   * @param username 用户名
   * @param passwordNew 新密码
   * @param forgetToken token
   * @return 返回结果
   */
  @RequestMapping(value = "/forgetRestPassword", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<String> forgetRestPassword(String username, String passwordNew,
      String forgetToken) {
    return iUserService.forgetRestPassword(username, passwordNew, forgetToken);
  }

  /**
   * 更新用户个人信息
   *
   * @param session 会话对象
   * @param user 用户信息
   * @return 返回结果
   */
  @RequestMapping(value = "/updateInfomation.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse<User> updateInfomation(HttpSession session, User user) {
    User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
    if (currentUser == null) {
      return ServerResponse.createByErrorMessage("用户未登录");
    }
    user.setId(currentUser.getId());
    user.setUsername(currentUser.getUsername());
    ServerResponse<User> response = iUserService.updateInformation(user);
    if (response.isSuccess()) {
      session.setAttribute(Const.CURRENT_USER, response.getData());
    }
    return response;
  }

  /**
   * 获取用户详细信息
   *
   * @param session 会话对象
   * @return 返回用户详细信息
   */
  @RequestMapping(value = "/getInformation.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse<User> getInformation(HttpSession session) {
    User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
    if (currentUser == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，需要强制登陆 status=10");
    }
    return iUserService.getInformation(currentUser.getId());
  }
}
