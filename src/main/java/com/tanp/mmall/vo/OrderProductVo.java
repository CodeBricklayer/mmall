package com.tanp.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author CodeBricklayer
 * @date 2019/11/6 11:43
 * @description TODO
 */
public class OrderProductVo {

  private List<OrderItemVo> orderItemVoList;

  private BigDecimal productTotalPrice;

  private String imageHost;

  public List<OrderItemVo> getOrderItemVoList() {
    return orderItemVoList;
  }

  public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
    this.orderItemVoList = orderItemVoList;
  }

  public BigDecimal getProductTotalPrice() {
    return productTotalPrice;
  }

  public void setProductTotalPrice(BigDecimal productTotalPrice) {
    this.productTotalPrice = productTotalPrice;
  }

  public String getImageHost() {
    return imageHost;
  }

  public void setImageHost(String imageHost) {
    this.imageHost = imageHost;
  }
}
