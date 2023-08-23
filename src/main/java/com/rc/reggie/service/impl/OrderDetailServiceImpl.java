package com.rc.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.reggie.entity.OrderDetail;
import com.rc.reggie.entity.Orders;
import com.rc.reggie.mapper.OrderDetailMapper;
import com.rc.reggie.mapper.OrderMapper;
import com.rc.reggie.service.OrderDetailService;
import com.rc.reggie.service.OrderService;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
