package com.tanp.mmall.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author CodeBricklayer
 * @date 2019/11/6 11:44
 * @description TODO
 */
@Data
public class OrderVo {

  private Long orderNo;

  private BigDecimal payment;

  private Integer paymentType;

  private String paymentTypeDesc;

  private Integer postage;

  private Integer status;

  private String statusDesc;

  private String paymentTime;

  private String sendTime;

  private String endTime;

  private String closeTime;

  private String createTime;

  //订单明细
  private List<OrderItemVo> orderItemVoList;

  private String imageHost;

  private Integer shippingId;

  private String receiverName;

  private ShippingVo shippingVo;

}
