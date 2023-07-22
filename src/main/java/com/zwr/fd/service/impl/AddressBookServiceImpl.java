package com.zwr.fd.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.AddressBook;
import com.zwr.fd.mapper.AddressBookMapper;
import com.zwr.fd.service.AddressBookService;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService{

}
