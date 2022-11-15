package com.example.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie_take_out.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Su
 * @Date: 2022-11-14-10:49
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
