package com.tanp.mmall.service;

import com.github.pagehelper.PageInfo;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.pojo.Product;
import com.tanp.mmall.vo.ProductDetailVo;

public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSalesStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);
}
