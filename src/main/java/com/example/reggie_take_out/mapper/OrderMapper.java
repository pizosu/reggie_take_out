package com.example.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie_take_out.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Su
 * @Date: 2022-11-14-16:51
 * @Description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
