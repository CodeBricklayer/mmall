package com.tanp.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.service.IProductService;
import com.tanp.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 列表，搜索，动态排序功能
 * @author PangT
 * @since 2019/1/4
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    /**
     * 前台商品详情
     * @param productId 商品id
     * @return 返回结果
     */
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    /**
     * 列表，搜索，动态排序功能
     * @param keyword 关键字
     * @param categoryId 商品id
     * @param pageNum 页码
     * @param pageSize 页面数量
     * @param orderBy 排序字段
     * @return 返回结果
     */
    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "")String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum, pageSize,orderBy);
    }
}
