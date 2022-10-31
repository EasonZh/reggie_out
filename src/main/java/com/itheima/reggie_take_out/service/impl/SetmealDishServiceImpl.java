package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.dto.SetmealDto;
import com.itheima.reggie_take_out.entity.Setmeal;
import com.itheima.reggie_take_out.entity.SetmealDish;
import com.itheima.reggie_take_out.mapper.SetmealDishMapper;
import com.itheima.reggie_take_out.mapper.SetmealMapper;
import com.itheima.reggie_take_out.service.SetmealDishService;
import com.itheima.reggie_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {


}
