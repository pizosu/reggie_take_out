package com.example.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_take_out.dto.DishDto;
import com.example.reggie_take_out.entity.Dish;

/**
 * @Author: Su
 * @Date: 2022-11-10-15:28
 * @Description:
 */

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表:dish,dishFlavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和口味信息
    public DishDto getWithFlavor(Long id);

    //修改菜品信息和口味信息
    public void updateWithFlavor(DishDto dishDto);

    //删除菜品信息和口味信息
    public void deleteWithFlavor(String id);
}
