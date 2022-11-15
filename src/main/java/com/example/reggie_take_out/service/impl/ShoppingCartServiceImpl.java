package com.example.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_take_out.entity.ShoppingCart;
import com.example.reggie_take_out.mapper.ShoppingCartMapper;
import com.example.reggie_take_out.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @Author: Su
 * @Date: 2022-11-14-15:33
 * @Description:
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
