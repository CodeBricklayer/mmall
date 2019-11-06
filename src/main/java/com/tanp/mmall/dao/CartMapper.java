package com.tanp.mmall.dao;

import com.tanp.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Cart record);

  int insertSelective(Cart record);

  Cart selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Cart record);

  int updateByPrimaryKey(Cart record);

  /**
   * 根据用户Id和产品Id去查购物车
   *
   * @param userId 用户ID
   * @param productId 产品ID
   * @return 购物车
   */
  Cart selectCartByUserIdProductId(@Param("userId") Integer userId,
      @Param("productId") Integer productId);

  /**
   * 根据用户Id查询购物车
   *
   * @param userId 用户ID
   * @return 购物车
   */
  List<Cart> selectCartByUserId(Integer userId);

  /**
   * 根据用户Id查询商品是否被选中
   *
   * @param userId 用户ID
   * @return 被选中数量
   */
  int selectCartProductCheckedStatusByUserId(Integer userId);

  /**
   * 删除购物车中的商品
   *
   * @param userId 用户ID
   * @param productIdList 产品列表
   * @return 删除购物车中的商品的数量
   */
  int deleteByUserIdProductIds(@Param("userId") Integer userId,
      @Param("productIdList") List<String> productIdList);

  /**
   * 全选购物车中的商品或者不全选购物车中的商品
   *
   * @param userId 用户ID
   * @param productId 产品ID
   * @param checked 选中标识
   * @return 数量
   */
  int checkedOrUncheckedProduct(@Param("userId") Integer userId,
      @Param("productId") Integer productId, @Param("checked") Integer checked);

  /**
   * 根据用户Id查询购物车中商品的数量
   *
   * @param userId 用户ID
   * @return 购物车中商品的数量
   */
  int selectCartProductCount(@Param("userId") Integer userId);

  /**
   * 查询购物车中的商品是否勾选
   *
   * @param userId 用户ID
   * @return 商品是否勾选
   */
  List<Cart> selectCheckedCartByUserId(Integer userId);
}