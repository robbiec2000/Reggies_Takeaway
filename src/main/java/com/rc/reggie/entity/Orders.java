package com.rc.reggie.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    //1not paid，2delivering，3delivered，4completed，5cancelled
    private Integer status;


    private Long userId;

    private Long addressBookId;


    private LocalDateTime orderTime;


    private LocalDateTime checkoutTime;


    private Integer payMethod;


    private BigDecimal amount;

    private String remark;

    private String userName;

    private String phone;

    private String address;

    private String consignee;
}
