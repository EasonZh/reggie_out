package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.R;
import com.itheima.reggie_take_out.entity.Orders;
import com.itheima.reggie_take_out.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String>submit(@RequestBody Orders orders){

        ordersService.submit(orders);
        return R.success("插入订单成功");
    }
}
