package com.example.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_take_out.entity.AddressBook;
import com.example.reggie_take_out.mapper.AddressBookMapper;
import com.example.reggie_take_out.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @Author: Su
 * @Date: 2022-11-14-14:17
 * @Description:
 */

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
