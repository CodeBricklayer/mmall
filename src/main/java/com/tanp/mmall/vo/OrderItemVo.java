package com.tanp.mmall.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author CodeBricklayer
 * @date 2019/11/6 11:42
 * @description TODO
 */
@Data
public class OrderItemVo {

  private Long orderNo;

  private Integer productId;

  private String productName;

  private String productImage;

  private BigDecimal currentUnitPrice;

  private Integer quantity;

  private BigDecimal totalPrice;

  private String createTime;

}
