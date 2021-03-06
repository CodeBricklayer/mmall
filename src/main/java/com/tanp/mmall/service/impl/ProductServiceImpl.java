package com.tanp.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tanp.mmall.common.Const;
import com.tanp.mmall.common.ResponseCode;
import com.tanp.mmall.common.ServerResponse;
import com.tanp.mmall.dao.CategoryMapper;
import com.tanp.mmall.dao.ProductMapper;
import com.tanp.mmall.pojo.Category;
import com.tanp.mmall.pojo.Product;
import com.tanp.mmall.service.ICategoryService;
import com.tanp.mmall.service.IProductService;
import com.tanp.mmall.util.DateTimeUtil;
import com.tanp.mmall.util.PropertiesUtil;
import com.tanp.mmall.vo.ProductDetailVo;
import com.tanp.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台商品新增，保存，更新，上下架功能开发
 *
 * @author PangT
 * @since 2018/12/26
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  protected CategoryMapper categoryMapper;

  @Autowired
  private ICategoryService iCategoryService;

  @Override
  public ServerResponse saveOrUpdateProduct(Product product) {
    if (product != null) {
      if (StringUtils.isNotBlank(product.getSubImages())) {
        String[] subImageArray = product.getSubImages().split(",");
        if (subImageArray.length > 0) {
          product.setMainImage(subImageArray[0]);
        }
      }
      if (product.getId() != null) {
        int rowCount = productMapper.updateByPrimaryKey(product);
        if (rowCount > 0) {
          return ServerResponse.createBySuccessMessage("更新商品成功");
        }
        return ServerResponse.createByErrorMessage("更新商品失败");
      } else {
        int rowCount = productMapper.insert(product);
        if (rowCount > 0) {
          return ServerResponse.createBySuccessMessage("新增商品成功");
        }
        return ServerResponse.createByErrorMessage("新增商品失败");
      }
    }
    return ServerResponse.createByErrorMessage("新增或更新商品参数不正确");
  }

  @Override
  public ServerResponse<String> setSalesStatus(Integer productId, Integer status) {
    if (productId == null || status == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
          ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    Product product = new Product();
    product.setId(productId);
    product.setStatus(status);
    int rowCount = productMapper.updateByPrimaryKeySelective(product);
    if (rowCount > 0) {
      return ServerResponse.createBySuccessMessage("修改商品销售状态成功");
    }
    return ServerResponse.createByErrorMessage("修改商品销售状态失败");
  }

  @Override
  public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
    if (productId == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
          ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    Product product = productMapper.selectByPrimaryKey(productId);
    if (product == null) {
      return ServerResponse.createByErrorMessage("商品已下架或者删除");
    }
    //VO对象 -- value object
    //POJO-BO(business object) --> VO(view object)
    ProductDetailVo productDetailVo = assembleProductDetailVo(product);
    return ServerResponse.createBySuccess(productDetailVo);
  }

  private ProductDetailVo assembleProductDetailVo(Product product) {
    ProductDetailVo productDetailVo = new ProductDetailVo();
    productDetailVo.setId(product.getId());
    productDetailVo.setSubtitle(product.getSubtitle());
    productDetailVo.setPrice(product.getPrice());
    productDetailVo.setMainImage(product.getMainImage());
    productDetailVo.setSubImage(product.getSubImages());
    productDetailVo.setCategoryId(product.getCategoryId());
    productDetailVo.setDetail(product.getDetail());
    productDetailVo.setName(product.getName());
    productDetailVo.setStatus(product.getStatus());
    productDetailVo.setStock(product.getStock());
    productDetailVo
        .setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com"));

    Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
    if (category == null) {
      //默认根节点
      productDetailVo.setParentCategoryId(0);
    } else {
      productDetailVo.setParentCategoryId(category.getParentId());
    }
    productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
    productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
    return productDetailVo;
  }

  @Override
  public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
    //startPage--start
    //填充自己的sql查询逻辑
    //pageHelper-收尾

    PageHelper.startPage(pageNum, pageSize);
    List<Product> productList = productMapper.selectList();

    List<ProductListVo> productListVoList = Lists.newArrayList();
    for (Product productItem : productList) {
      ProductListVo productListVo = assembleProductListVo(productItem);
      productListVoList.add(productListVo);
    }
    PageInfo pageResult = new PageInfo(productList);
    pageResult.setList(productListVoList);
    return ServerResponse.createBySuccess(pageResult);
  }

  private ProductListVo assembleProductListVo(Product product) {
    ProductListVo productListVo = new ProductListVo();
    productListVo.setId(product.getId());
    productListVo.setName(product.getName());
    productListVo.setCategoryId(product.getCategoryId());
    productListVo
        .setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.mmall.com"));
    productListVo.setMainImage(product.getMainImage());
    productListVo.setPrice(product.getPrice());
    productListVo.setSubTitle(product.getSubtitle());
    productListVo.setStatus(product.getStatus());
    return productListVo;
  }

  @Override
  public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum,
      int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    if (StringUtils.isNotBlank(productName)) {
      productName = new StringBuilder().append("%").append(productName).append("%").toString();
    }
    List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
    List<ProductListVo> productListVoList = Lists.newArrayList();
    for (Product productItem : productList) {
      ProductListVo productListVo = assembleProductListVo(productItem);
      productListVoList.add(productListVo);
    }
    PageInfo pageResult = new PageInfo(productList);
    pageResult.setList(productListVoList);
    return ServerResponse.createBySuccess(pageResult);
  }

  @Override
  public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
    if (productId == null) {
      return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
          ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
    Product product = productMapper.selectByPrimaryKey(productId);
    if (product == null) {
      return ServerResponse.createByErrorMessage("商品已下架或者删除");
    }
    if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
      return ServerResponse.createByErrorMessage("商品已下架或者删除");
    }
    ProductDetailVo productDetailVo = assembleProductDetailVo(product);
    return ServerResponse.createBySuccess(productDetailVo);
  }

  @Override
  public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
      int pageNum, int pageSize, String orderBy) {
    List<Integer> categoryIdList = new ArrayList<>();
    if (categoryId != null) {
      Category category = categoryMapper.selectByPrimaryKey(categoryId);

      if (category == null && StringUtils.isBlank(keyword)) {
        //没有该分类，并且还没有关键字，这个时候返回一个空的集合，不能返回报错
        PageHelper.startPage(pageNum, pageSize);
        List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
        PageInfo pageInfo = new PageInfo(productDetailVoList);
        return ServerResponse.createBySuccess(pageInfo);
      }
      categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
    }
    if (StringUtils.isNotBlank(keyword)) {
      keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
    }

    PageHelper.startPage(pageNum, pageSize);
    //排序处理
    if (StringUtils.isNotBlank(orderBy)) {
      if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
        String[] orderByArray = orderBy.split("_");
        PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
      }
    }
    List<Product> productList = productMapper
        .selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword,
            categoryIdList.size() == 0 ? null : categoryIdList);

    List<ProductListVo> productListVoList = Lists.newArrayList();
    for (Product product : productList) {
      ProductListVo productListVo = assembleProductListVo(product);
      productListVoList.add(productListVo);
    }

    //开始分页
    PageInfo pageInfo = new PageInfo(productList);

    pageInfo.setList(productListVoList);

    return ServerResponse.createBySuccess(pageInfo);

  }
}
