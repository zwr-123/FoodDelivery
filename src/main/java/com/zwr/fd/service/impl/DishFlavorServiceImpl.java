package com.zwr.fd.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.DishFlavor;
import com.zwr.fd.mapper.DishFlavorMapper;
import com.zwr.fd.service.DishFlavorService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService{

}
