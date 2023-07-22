package com.zwr.fd.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.User;
import com.zwr.fd.mapper.UserMapper;
import com.zwr.fd.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
