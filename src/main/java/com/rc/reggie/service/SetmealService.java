package com.rc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.reggie.dto.DishDto;
import com.rc.reggie.dto.SetmealDto;
import com.rc.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);

    void updateWithDish(SetmealDto setmealDto);
}
