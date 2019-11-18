package com.tanp.mmall.vo;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductListVo {

  private Integer id;

  private Integer categoryId;

  private String name;

  private String subTitle;

  private String mainImage;

  private BigDecimal price;

  private Integer status;

  private String imageHost;

}
