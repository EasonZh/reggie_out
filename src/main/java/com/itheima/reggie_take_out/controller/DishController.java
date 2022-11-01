package com.itheima.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take_out.common.R;
import com.itheima.reggie_take_out.dto.DishDto;
import com.itheima.reggie_take_out.entity.Category;
import com.itheima.reggie_take_out.entity.Dish;
import com.itheima.reggie_take_out.service.CategoryService;
import com.itheima.reggie_take_out.service.DishFlavourService;
import com.itheima.reggie_take_out.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavourService dishFlavourService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping
    public R<String>save(@RequestBody DishDto dishDto){
           dishService.saveWithFlavor(dishDto);
           return R.success("新增菜品成功！");
    }
    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){
        //构造分页构造器对象
        Page <Dish>pageInfo = new Page(page,pageSize);

        Page <DishDto> dishDtoPage = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //增加过滤条件
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish>records = pageInfo.getRecords();
        List<DishDto>list =records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            //根据id分类查询
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                dishDto.setCategoryName(category.getName());

            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);

    }
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);

    }
    @PutMapping
    public R<String>update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        //清理缓存
        String key = "dish"+dishDto.getCategoryId()+"_"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("更新菜品成功！");
    }
    @GetMapping("/list")
    public R<List<Dish>>list(Dish dish){
        List<Dish> list = null;
        String dishId = "dish"+dish.getCategoryId()+"_"+dish.getStatus();
        //从redis中获取数据
         list = (List<Dish>) redisTemplate.opsForValue().get(dishId);
         //如果存在，直接获取
        if(list!=null){
            return R.success(list);
        }


        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        //查询状态是1的
        queryWrapper.eq(Dish::getStatus,1);
        list = dishService.list(queryWrapper);
        //如果不存在，则查完数据库后，再存到redis中
        redisTemplate.opsForValue().set(dishId,list,1, TimeUnit.HOURS);
        return R.success(list);
    }


}
