package com.example.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie_take_out.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Su
 * @Date: 2022-11-10-15:27
 * @Description:
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
