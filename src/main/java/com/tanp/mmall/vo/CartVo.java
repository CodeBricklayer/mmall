package com.tanp.mmall.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CartVo {

  private List<CartProductVo> cartProductVoList;
  private BigDecimal cartTotalPrice;
  //是否已经都勾选
  private Boolean allChecked;
  private String imageHost;
}
