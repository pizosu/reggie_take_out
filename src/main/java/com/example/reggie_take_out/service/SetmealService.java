package com.example.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_take_out.dto.SetmealDto;
import com.example.reggie_take_out.entity.Setmeal;

import java.util.List;

/**
 * @Author: Su
 * @Date: 2022-11-10-15:29
 * @Description:
 */

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时要删除套餐表和菜品的关系联系表
     * @param ids
     */
    public void deleteWithDish(List<Long> ids);

    /**
     * 更新套餐状态
     * @param id
     * @param ids
     */
    public void updateStatus(Integer id,List<Long> ids);

    /**
     * 获取修改页面中的套餐信息和套餐菜品信息
     * @param id
     * @return
     */
    public SetmealDto getWithSetmealDish(Long id);

    /**
     * 更改setmeal表和setmealDish表
     * @param setmealDto
     */
    public void updateWithSetmealDish(SetmealDto setmealDto);

}
