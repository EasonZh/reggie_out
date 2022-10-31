package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.entity.DishFlavor;
import com.itheima.reggie_take_out.mapper.DishFlavourMapper;
import com.itheima.reggie_take_out.service.DishFlavourService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavourImpl extends ServiceImpl<DishFlavourMapper,DishFlavor> implements DishFlavourService {
}
