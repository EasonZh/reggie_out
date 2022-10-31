package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.entity.Category;
import com.itheima.reggie_take_out.entity.Employee;


public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
