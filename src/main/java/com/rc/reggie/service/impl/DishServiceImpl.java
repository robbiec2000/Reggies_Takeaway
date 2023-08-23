package com.rc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.reggie.common.CustomException;
import com.rc.reggie.dto.DishDto;
import com.rc.reggie.entity.Dish;
import com.rc.reggie.entity.DishFlavor;
import com.rc.reggie.entity.SetmealDish;
import com.rc.reggie.mapper.DishMapper;
import com.rc.reggie.service.DishFlavorService;
import com.rc.reggie.service.DishService;
import com.rc.reggie.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    SetmealDishService setmealDishService;

    @Value("${reggie.path}")
    private String basePath;


    @Override
    @Transactional
    public void saveFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();

        List<DishFlavor> dishFlavorList = dishDto.getFlavors();
        dishFlavorList.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(dishFlavorList);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        Long dishId = dishDto.getId();

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishId);
        dishFlavorService.remove(queryWrapper);


        List<DishFlavor> dishFlavorList = dishDto.getFlavors();
        dishFlavorList.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    @Transactional
    public void remove(List<Long> ids) {

        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.in(SetmealDish::getDishId, ids);
        int count = setmealDishService.count(qw);

        if(count > 0){
            throw new CustomException("delete fail, category associated with dishes");
        }

        LambdaQueryWrapper<DishFlavor> qw2 = new LambdaQueryWrapper<>();
        qw2.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(qw2);
        this.removeByIds(ids);
        /*String filename = this.getById(id).getImage();
        this.removeById(id);
        File file = new File(basePath + filename);
        if(file.exists()){
            file.delete();
        }*/
    }
}
