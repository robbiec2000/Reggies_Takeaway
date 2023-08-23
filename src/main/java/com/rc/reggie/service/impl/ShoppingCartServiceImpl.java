package com.rc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.reggie.entity.SetmealDish;
import com.rc.reggie.entity.ShoppingCart;
import com.rc.reggie.mapper.SetmealDishMapper;
import com.rc.reggie.mapper.ShoppingCartMapper;
import com.rc.reggie.service.SetmealDishService;
import com.rc.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
