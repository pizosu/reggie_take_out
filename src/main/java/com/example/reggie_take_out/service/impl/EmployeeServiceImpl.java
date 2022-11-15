package com.example.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_take_out.entity.Employee;
import com.example.reggie_take_out.mapper.EmployeeMapper;
import com.example.reggie_take_out.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author: Su
 * @Date: 2022-11-07-16:41
 * @Description:
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
