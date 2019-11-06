package com.tanp.mmall.dao;

import com.tanp.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Product record);

  int insertSelective(Product record);

  Product selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Product record);

  int updateByPrimaryKey(Product record);

  List<Product> selectList();

  /**
   * 产品的模糊查询
   *
   * @param productName 产品名称
   * @param productId 产品ID
   * @return 产品
   */
  List<Product> selectByNameAndProductId(@Param("productName") String productName,
      @Param("productId") Integer productId);

  /**
   * 根据传过来的产品Id，然后将商品添加到购物车中
   *
   * @param productName 产品名称
   * @param categoryIdList 产品ID
   * @return 产品
   */
  List<Product> selectByNameAndCategoryIds(@Param("productName") String productName,
      @Param("categoryIdList") List<Integer> categoryIdList);
}