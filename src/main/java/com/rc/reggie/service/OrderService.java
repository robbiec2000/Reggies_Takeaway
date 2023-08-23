package com.rc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.reggie.entity.Category;
import com.rc.reggie.entity.Orders;


public interface OrderService extends IService<Orders> {

    void submit(Orders orders);
}
