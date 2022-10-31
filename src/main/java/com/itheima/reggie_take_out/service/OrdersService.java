package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.entity.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
