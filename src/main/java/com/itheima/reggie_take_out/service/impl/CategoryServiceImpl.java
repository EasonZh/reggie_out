package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.CustomException;
import com.itheima.reggie_take_out.entity.Category;
import com.itheima.reggie_take_out.entity.Dish;
import com.itheima.reggie_take_out.entity.Employee;
import com.itheima.reggie_take_out.entity.Setmeal;
import com.itheima.reggie_take_out.mapper.CategoryMapper;
import com.itheima.reggie_take_out.mapper.EmployeeMapper;
import com.itheima.reggie_take_out.service.CategoryService;
import com.itheima.reggie_take_out.service.DishService;
import com.itheima.reggie_take_out.service.EmployeeService;
import com.itheima.reggie_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    public DishService dishService;
    @Autowired
    public SetmealService setmealService;
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,ids);
        int count1 = dishService.count(queryWrapper);
        if(count1>0){
              throw new CustomException("当前分类关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal>queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Setmeal::getCategoryId,ids);
        int count2 = setmealService.count(queryWrapper1);
        if(count2>0){
            throw new CustomException("当前分类关联了套餐，不能删除");
        }
        super.removeById(ids);
    }
}
