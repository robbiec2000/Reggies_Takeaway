package com.rc.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.reggie.common.R;
import com.rc.reggie.dto.DishDto;
import com.rc.reggie.dto.SetmealDto;
import com.rc.reggie.entity.Category;
import com.rc.reggie.entity.Dish;
import com.rc.reggie.entity.Setmeal;
import com.rc.reggie.entity.SetmealDish;
import com.rc.reggie.service.CategoryService;
import com.rc.reggie.service.DishService;
import com.rc.reggie.service.SetmealDishService;
import com.rc.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("add combo success");
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("update combo success");
    }

    @GetMapping("/page")
    public R<Page> getAll(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long id = item.getCategoryId();
            Category c = categoryService.getById(id);
            if(c != null){
                setmealDto.setCategoryName(c.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,  Setmeal::getStatus, 1 );
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);

    }

    @GetMapping("/{id}")
    public R<SetmealDto> getOne(@PathVariable Long id){


        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = setmealDishService.list(qw);

        setmealDto.setSetmealDishes(setmealDishes);

        Category c = categoryService.getById(setmeal.getCategoryId());
        setmealDto.setCategoryName(c.getName());

        return R.success(setmealDto);

    }

    @PostMapping("/status/{value}")
    public R<String> setStatus(@PathVariable int value, @RequestParam List<Long> ids){

        List<Setmeal> setmeals = setmealService.listByIds(ids);
        setmeals.stream().map((item)->{
            item.setStatus(value);
            return item;
        }).collect(Collectors.toList());

        setmealService.updateBatchById(setmeals);
        return R.success("combo status update success");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("delete combo success");
    }


}
