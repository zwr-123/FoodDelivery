package com.zwr.fd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.OrderDetail;
import com.zwr.fd.mapper.OrderDetailMapper;
import com.zwr.fd.service.OrderDetailService;

import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}