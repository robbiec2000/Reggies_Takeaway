package com.rc.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.reggie.entity.OrderDetail;
import com.rc.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
