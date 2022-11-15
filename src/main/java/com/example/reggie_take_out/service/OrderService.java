package com.example.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_take_out.dto.OrderDto;
import com.example.reggie_take_out.entity.Orders;

/**
 * @Author: Su
 * @Date: 2022-11-14-16:52
 * @Description:
 */
public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * @param order
     */
    void submit(Orders order);

    /**
     * 用户查询订单
     * @param pageInfo
     * @return
     */
    Page<OrderDto> getPage(Page<Orders> pageInfo);
}
