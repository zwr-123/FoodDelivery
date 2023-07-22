package com.zwr.fd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.ShoppingCart;
import com.zwr.fd.mapper.ShoppingCartMapper;
import com.zwr.fd.service.ShoppingCartService;

import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
