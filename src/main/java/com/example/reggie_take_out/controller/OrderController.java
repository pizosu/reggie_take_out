package com.example.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_take_out.common.BaseContext;
import com.example.reggie_take_out.common.R;
import com.example.reggie_take_out.dto.OrderDto;
import com.example.reggie_take_out.entity.OrderDetail;
import com.example.reggie_take_out.entity.Orders;
import com.example.reggie_take_out.entity.ShoppingCart;
import com.example.reggie_take_out.service.OrderDetailService;
import com.example.reggie_take_out.service.OrderService;
import com.example.reggie_take_out.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Su
 * @Date: 2022-11-14-16:55
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 用户下单
     * @param order
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order){
        log.info("下单数据：{}",order);
        orderService.submit(order);
        return R.success("下单成功!");
    }

    /**
     * 查询订单数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(int page,int pageSize){
        log.info("page = {},pageSize = {}",page,pageSize);
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrderDto> orderDtoPage = orderService.getPage(pageInfo);

        return R.success(orderDtoPage);
    }

    /**
     * 分页查询订单明细表
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime,String  endTime){
        log.info("page = {},pageSize = {}, number = {}, beginTime = {}, endTime = {}",page,pageSize,number,beginTime,endTime);
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number!=null,Orders::getNumber,number);
        queryWrapper.gt(StringUtils.isNotBlank(beginTime),Orders::getOrderTime,beginTime);   //orderTime > beginTime
        queryWrapper.lt(StringUtils.isNotBlank(endTime),Orders::getOrderTime,endTime);       //orderTime < endTime

        orderService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 再来一单
     * @param map
     * @return
     */
    @PostMapping("/again")
    public R<String> again(@RequestBody Map<String,String> map){
        log.info("再来一单的订单为：{}",map);
        String id = map.get("id");
        long ids = Long.parseLong(id);

        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId,ids);
        List<OrderDetail> orderDetailList = orderDetailService.list(wrapper);
        Long userId = BaseContext.getCurrentId();

        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map((item) -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());
            Long dishId = item.getDishId();
            Long setmealId = item.getSetmealId();
            if (dishId != null) {
                //如果是菜品那就添加菜品的查询条件
                shoppingCart.setDishId(dishId);
            } else {
                //添加到购物车的是套餐
                shoppingCart.setSetmealId(setmealId);
            }
            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        shoppingCartService.saveBatch(shoppingCartList);

        return R.success("操作成功");
    }

    /**
     * 更改订单状态
     * @param orders
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        log.info("当前的订单状态为：{}",orders.toString());
        orders.setStatus(4);
        orderService.updateById(orders);
        return R.success("更改成功!");
    }
}
