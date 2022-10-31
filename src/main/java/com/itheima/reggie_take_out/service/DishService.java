package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.dto.DishDto;
import com.itheima.reggie_take_out.entity.Category;
import com.itheima.reggie_take_out.entity.Dish;


public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味
     void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应口味信息
     DishDto getByIdWithFlavor(Long id);
    //更新菜品信息
    void updateWithFlavor(DishDto dishDto);
}
