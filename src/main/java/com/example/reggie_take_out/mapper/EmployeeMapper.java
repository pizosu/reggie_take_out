package com.example.reggie_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie_take_out.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Su
 * @Date: 2022-11-07-16:38
 * @Description:
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
