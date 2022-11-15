package com.example.reggie_take_out.dto;

import com.example.reggie_take_out.entity.OrderDetail;
import com.example.reggie_take_out.entity.Orders;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Su
 * @Date: 2022-11-14-19:46
 * @Description:
 */
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
