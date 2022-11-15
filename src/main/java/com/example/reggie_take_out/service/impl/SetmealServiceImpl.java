package com.example.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_take_out.common.CustomException;
import com.example.reggie_take_out.common.R;
import com.example.reggie_take_out.dto.SetmealDto;
import com.example.reggie_take_out.entity.Setmeal;
import com.example.reggie_take_out.entity.SetmealDish;
import com.example.reggie_take_out.mapper.SetmealMapper;
import com.example.reggie_take_out.service.SetmealDishService;
import com.example.reggie_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Su
 * @Date: 2022-11-10-15:30
 * @Description:
 */
@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>implements SetmealService {

    @Resource
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);
        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，需要删除setmeal表和dish_setmeal表
     * @param ids
     */
    @Override
    @Transactional
    public void deleteWithDish(List<Long> ids) {
        //查询要删除的套餐的状态是否为1
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count > 0){
            //若处于出售状态，则抛出异常
            throw new CustomException("套餐出售中，不可出售");
        }
        //批量删除setmeal表
        this.removeByIds(ids);
        //批量删除dish_setmeal表
        LambdaQueryWrapper<SetmealDish> queryWrapperDish = new LambdaQueryWrapper<>();
        queryWrapperDish.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapperDish);
    }

    /**
     * 修改套餐状态
     * @param id
     * @param ids
     */
    @Override
    public void updateStatus(Integer id, List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> setmealList = this.list(queryWrapper);
        setmealList.forEach((item) -> {
            item.setStatus(id);
            this.updateById(item);
        });
    }

    /**
     * 根据id获取修改页面中的套餐信息和套餐菜品信息
     * @param id
     * @return
     */
    @Override
    public SetmealDto getWithSetmealDish(Long id) {
        //获取setmeal中的属性
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = this.getById(id);
        //将其拷贝给setmealDto
        BeanUtils.copyProperties(setmeal,setmealDto);
        //条件构造器
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);

        return setmealDto;
    }

    /**
     * 更改套餐表和SetmealDish表
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateWithSetmealDish(SetmealDto setmealDto) {
        //将数据保存到setmeal表
        this.updateById(setmealDto);

        //先删后增
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        //删除
        setmealDishService.remove(queryWrapper);
        //赋值
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //将dishId重新赋值
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存
        setmealDishService.saveBatch(setmealDishes);
    }

}
