package com.itheima.reggie_take_out.controller;

import ch.qos.logback.core.net.server.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie_take_out.common.R;
import com.itheima.reggie_take_out.entity.User;
import com.itheima.reggie_take_out.service.UserService;
import com.itheima.reggie_take_out.utils.ValidateCodeUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            session.setAttribute(phone,code);

            return R.success("登录成功");
        }
        return R.error("登录失败");
    }
    @PostMapping("/login")
    public R<String> login(@RequestBody Map map,HttpSession session){
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        Object attribute = session.getAttribute(phone);
        if(code.equals(attribute)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success("登录成功");
        }

        return R.error("登录失败");
    }
}
