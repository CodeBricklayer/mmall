package com.tanp.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.Product;
import com.tanp.mmall.pojo.User;
import com.tanp.mmall.service.IFileService;
import com.tanp.mmall.service.IProductService;
import com.tanp.mmall.service.IUserService;
import com.tanp.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Properties;

/**
 * 商品管理
 *
 * @author PangT
 * @since 2018/12/26
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    /**
     * 增加或者更新商品
     *
     * @param session 会话对象
     * @param product 商品
     * @return 返回操作结果
     */
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 修改销售商品状态,即商品上下架
     *
     * @param session   会话对象
     * @param productId 商品id
     * @param status    商品状态
     * @return 返回操作结果
     */
    @RequestMapping(value = "/setSaleStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSalesStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 获取商品详情
     *
     * @param session   会话对象
     * @param productId 商品Id
     * @return 返回获取结果
     */
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 后台商品列表
     *
     * @param session  会话对象
     * @param pageNum  页码
     * @param pageSize 页面数量
     * @return 返回列表数据
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 查询商品
     *
     * @param session     会话对象
     * @param productName 商品名称
     * @param productId   商品id
     * @param pageNum     页码
     * @param pageSize    页面数量
     * @return 返回查询结果
     */
    @RequestMapping(value = "/search.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,
                                        String productName, Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * spring mvc 文件上传
     *
     * @param session 会话对象
     * @param file    文件
     * @param request 请求
     * @return 返回结果
     */
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String tartgetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + tartgetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", tartgetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    /**
     * 富文本上传
     *
     * @param session  会话对象
     * @param file     上传文件
     * @param request  请求
     * @param response 响应
     * @return 返回结果
     */
    @RequestMapping(value = "/richtextImgUpload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }

        //富文本中对于返回值有自己的要求，我们使用是simditor所以按照simditor的要求进行返回
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作，需要管理员权限");
            return resultMap;
        }
    }
}
