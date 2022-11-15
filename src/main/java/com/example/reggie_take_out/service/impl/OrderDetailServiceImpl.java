package com.example.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_take_out.entity.OrderDetail;
import com.example.reggie_take_out.mapper.OrderDetailMapper;
import com.example.reggie_take_out.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author: Su
 * @Date: 2022-11-14-16:54
 * @Description:
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
