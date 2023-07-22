package com.zwr.fd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwr.fd.beans.Dish;
import com.zwr.fd.dto.DishDto;

public interface DishService extends IService<Dish>{
	 //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

	public DishDto getByIdWithFlavor(Long id);

	public void updateWithFlavor(DishDto dishDto);
}
