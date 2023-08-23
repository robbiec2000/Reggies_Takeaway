package com.rc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.reggie.common.CustomException;
import com.rc.reggie.entity.Category;
import com.rc.reggie.entity.Dish;
import com.rc.reggie.entity.Setmeal;
import com.rc.reggie.mapper.CategoryMapper;
import com.rc.reggie.service.CategoryService;
import com.rc.reggie.service.DishService;
import com.rc.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);

        if(count > 0){
            throw new CustomException("delete fail, category associated with dishes");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        count = setmealService.count(setmealLambdaQueryWrapper);

        if(count > 0){
            throw new CustomException("delete fail, category associated with dishes");
        }

        super.removeById(id);
    }
}
