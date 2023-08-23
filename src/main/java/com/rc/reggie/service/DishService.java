package com.rc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.reggie.dto.DishDto;
import com.rc.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    void saveFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void remove(List<Long> ids);
}
