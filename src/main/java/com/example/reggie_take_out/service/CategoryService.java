package com.example.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_take_out.entity.Category;

/**
 * @Author: Su
 * @Date: 2022-11-10-11:40
 * @Description:
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
