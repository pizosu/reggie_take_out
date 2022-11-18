package com.example.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_take_out.common.R;
import com.example.reggie_take_out.dto.SetmealDto;
import com.example.reggie_take_out.entity.Category;
import com.example.reggie_take_out.entity.Dish;
import com.example.reggie_take_out.entity.Setmeal;
import com.example.reggie_take_out.entity.SetmealDish;
import com.example.reggie_take_out.service.CategoryService;
import com.example.reggie_take_out.service.DishService;
import com.example.reggie_take_out.service.SetmealDishService;
import com.example.reggie_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Su
 * @Date: 2022-11-11-17:00
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private DishService dishService;

    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @CacheEvict(value = "setmealCache",allEntries = true)   //删除所有setmealCached的缓存数据
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息：{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增成功");
    }
    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo,dtoPage);
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmealCache",allEntries = true)   //删除所有setmealCached的缓存数据
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("删除的id为：{}",ids);
        setmealService.deleteWithDish(ids);
        return R.success("删除成功!");
    }

    /**
     * 修改套餐状态
     * @param id
     * @param ids
     * @return
     */
    @PostMapping("/status/{id}")
    public R<String> updateStatus(@PathVariable Integer id,@RequestParam List<Long> ids){
        log.info("当前套餐的状态:{},ids为：{}",id,ids);
        setmealService.updateStatus(id,ids);
        return R.success("修改状态成功!");
    }

    /**
     * 根据id查询套餐信息和套餐菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
        log.info("id是:{}",id);
        SetmealDto setmealDto = setmealService.getWithSetmealDish(id);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info("需要保存的套餐数据{}",setmealDto);
        setmealService.updateWithSetmealDish(setmealDto);
        return R.success("修改成功!");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId+'_'+#setmeal.status")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList = setmealService.list(queryWrapper);

        return R.success(setmealList);
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDto>> getDishList(@PathVariable Long id){
        log.info("套餐id为：{}",id);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishList = setmealDishService.list(queryWrapper);

        List<SetmealDto> setmealDtoList = setmealDishList.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long dishId = item.getDishId();
            Dish dish = dishService.getById(dishId);
            BeanUtils.copyProperties(dish, setmealDto);

            return setmealDto;
        }).collect(Collectors.toList());

        return R.success(setmealDtoList);
    }

}
