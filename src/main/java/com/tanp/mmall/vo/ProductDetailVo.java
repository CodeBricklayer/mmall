package com.tanp.mmall.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * VO:视图对象，用于展示层，它的作用是把某个指定页面（或组件）的所有数据封装起来。
 *
 * @author PangT
 * @since 2018/12/27
 */
@Data
public class ProductDetailVo {

  private Integer id;
  private Integer categoryId;
  private String name;
  private String subtitle;
  private String mainImage;
  private String subImage;
  private String detail;
  private BigDecimal price;
  private Integer stock;
  private Integer status;
  private String createTime;
  private String updateTime;

  private String imageHost;
  private Integer parentCategoryId;

}
