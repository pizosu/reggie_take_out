package com.example.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_take_out.entity.User;
import com.example.reggie_take_out.mapper.UserMapper;
import com.example.reggie_take_out.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: Su
 * @Date: 2022-11-14-10:50
 * @Description:
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
