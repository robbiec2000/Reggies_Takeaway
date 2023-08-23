package com.rc.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.reggie.common.R;
import com.rc.reggie.dto.DishDto;
import com.rc.reggie.entity.Category;
import com.rc.reggie.entity.Dish;
import com.rc.reggie.entity.DishFlavor;
import com.rc.reggie.service.CategoryService;
import com.rc.reggie.service.DishFlavorService;
import com.rc.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveFlavor(dishDto);
        return R.success("Add dish success");
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("Update dish success");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> dishList = pageInfo.getRecords();

        List<DishDto> list = dishList.stream().map((item) ->{

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long CategoryId = item.getCategoryId();
            Category category = categoryService.getById(CategoryId);

            if(category != null){
                dishDto.setCategoryName(category.getName());
            }else{
                dishDto.setCategoryName("No category");
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> getOne(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);

    }


    /*@GetMapping("/list")
    public R<List<Dish>> getByCategoryId(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishes = dishService.list(queryWrapper);

        return R.success(dishes);
    }*/

    @GetMapping("/list")
    public R<List<DishDto>> getByCategoryId(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishes = dishService.list(queryWrapper);

        List<DishDto> list = dishes.stream().map((item) ->{

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long CategoryId = item.getCategoryId();
            Category category = categoryService.getById(CategoryId);

            if(category != null){
                dishDto.setCategoryName(category.getName());
            }else{
                dishDto.setCategoryName("No Category");
            }

            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper1);

            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());


        return R.success(list);
    }

    @PostMapping("/status/{value}")
    public R<String> setStatus(@PathVariable int value, @RequestParam List<Long> ids){

        List<Dish> dishes = dishService.listByIds(ids);
        dishes.stream().map((item)->{
            item.setStatus(value);
            return item;
        }).collect(Collectors.toList());

        dishService.updateBatchById(dishes);
        return R.success("Dish status update success");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){

        dishService.remove(ids);
        return R.success("Dish delete success");
    }

}
