package com.rc.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.reggie.entity.Category;


public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
