package com.zwr.fd.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.Dish;
import com.zwr.fd.beans.DishFlavor;
import com.zwr.fd.dto.DishDto;
import com.zwr.fd.mapper.DishMapper;
import com.zwr.fd.service.DishFlavorService;
import com.zwr.fd.service.DishService;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	@Autowired
	private DishFlavorService dishFlavorService;

	@Override
	@Transactional
	public void saveWithFlavor(DishDto dishDto) {
		// TODO Auto-generated method stub
		// 保存菜品的基本信息到菜品表dish
		this.save(dishDto);

		Long dishId = dishDto.getId();// 菜品id

		// 菜品口味
		List<DishFlavor> flavors = dishDto.getFlavors();
		for (int i = 0; i < flavors.size(); i++) {
			flavors.get(i).setDishId(dishId);
		}

		// 保存菜品口味数据到菜品口味表dish_flavor
		dishFlavorService.saveBatch(flavors);
	}

	@Override
	public DishDto getByIdWithFlavor(Long id) {
		// TODO Auto-generated method stub
		DishDto dishDto = new DishDto();
		Dish dish = this.getById(id);
		BeanUtils.copyProperties(dish, dishDto);

		// 查询当前菜品对应的口味信息，从dish_flavor表查询
		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DishFlavor::getDishId, dish.getId());
		List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
		dishDto.setFlavors(flavors);

		return dishDto;
	}

	
	@Override
	@Transactional
	public void updateWithFlavor(DishDto dishDto) {
		this.updateById(dishDto);
		
		//清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        
		Long dishId = dishDto.getId();// 菜品id
		// 菜品口味
		List<DishFlavor> flavors = dishDto.getFlavors();
		for (int i = 0; i < flavors.size(); i++) {
			flavors.get(i).setDishId(dishId);
		}

		// 保存菜品口味数据到菜品口味表dish_flavor
		dishFlavorService.saveBatch(flavors);

	}

}
