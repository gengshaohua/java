package com.example.springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * order_info
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInfo implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 管家订单号
     */
    private String orderId;

    /**
     * 管家子订单号
     */
    private String subOrderId;

    /**
     * 吉致ID
     */
    private String channelOrderId;

    /**
     * 渠道资产ID
     */
    private String channelAssetsId;

    /**
     * 外部资产ID
     */
    private String nrmaAssetsId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 车型名称
     */
    private String modelName;

    /**
     * 车型编号
     */
    private String modelNo;

    /**
     * 颜色
     */
    private String carColor;

    /**
     * 车价
     */
    private String invoicePrice;

    /**
     * 购车城市
     */
    private String buyCarCity;

    /**
     * 上牌城市
     */
    private String registrationCity;

    /**
     * 订单保存状态
     */
    private Integer status;

    /**
     * 发票影像
     */
    private String invoiceImage;

    /**
     * 首付款证明影像
     */
    private String downPaymentImage;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 经度
     */
    private String lat;

    /**
     * 维度
     */
    private String lon;

    /**
     * 三要素校验结果
     */
    private String authResult;

    /**
     * kafka消息标识
     */
    private String apIds;


    private static final long serialVersionUID = 1L;
}