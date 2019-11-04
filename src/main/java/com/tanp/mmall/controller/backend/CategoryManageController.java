package com.tanp.mmall.controller.backend;

import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.ICategoryService;
import com.tanp.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 分类管理模块
 *
 * @author PangT
 * @since 2018/12/21
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

  @Autowired
  private IUserService iUserService;

  @Autowired
  private ICategoryService iCategoryService;

  /**
   * 添加产品分类
   *
   * @param session 会话对象
   * @param categoryName 分类名称
   * @param parentId 父类ID
   * @return 返回处理结果
   */
  @RequestMapping(value = "/addCategory.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse addCategory(HttpSession session, String categoryName,
      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
    }
    //校验一下是否是管理员
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //是管理员
      //增加我们处理分类的逻辑
      return iCategoryService.addCategory(categoryName, parentId);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }

  /**
   * 更新分类名称
   *
   * @param session 会话对象
   * @param categoryId 分类Id
   * @param categoryName 分类名称
   * @return 返回结果
   */
  @RequestMapping(value = "/setCategoryName.do", method = RequestMethod.POST)
  @ResponseBody
  public ServerResponse setCategoryName(HttpSession session, Integer categoryId,
      String categoryName) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
    }
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //更新CategoryName
      return iCategoryService.updateCategoryName(categoryId, categoryName);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }

  /**
   * 查询品类信息子节点，不递归
   *
   * @param session 会话对象
   * @param categoryId 品类Id
   * @return 返回结果集
   */
  @RequestMapping(value = "/getCategory.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse getChildrenParallelCategory(HttpSession session,
      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
    }
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //查询子节点的 category 信息，并且不递归，保持平级
      return iCategoryService.getChildrenParallelCategory(categoryId);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }

  /**
   * 查询当前节点的id和递归子节点的id
   *
   * @param session 会话对象
   * @param categoryId 品类id
   * @return 返回结果集
   */
  @RequestMapping(value = "getDeepCategory.do", method = RequestMethod.GET)
  @ResponseBody
  public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
    User user = (User) session.getAttribute(Const.CURRENT_USER);
    if (user == null) {
      return ServerResponse
          .createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
    }
    if (iUserService.checkAdminRole(user).isSuccess()) {
      //查询当前节点的id和递归子节点的id
      //0-->10000-->100000
      return iCategoryService.selectCategoryAndChildrenById(categoryId);
    } else {
      return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }
  }
}
