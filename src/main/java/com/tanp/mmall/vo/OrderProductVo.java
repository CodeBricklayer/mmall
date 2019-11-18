package com.tanp.mmall.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author CodeBricklayer
 * @date 2019/11/6 11:43
 * @description TODO
 */
@Data
public class OrderProductVo {

  private List<OrderItemVo> orderItemVoList;

  private BigDecimal productTotalPrice;

  private String imageHost;

}
