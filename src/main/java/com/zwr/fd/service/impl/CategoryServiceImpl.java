package com.zwr.fd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.Category;
import com.zwr.fd.beans.Dish;
import com.zwr.fd.beans.Setmeal;
import com.zwr.fd.common.CustomException;
import com.zwr.fd.mapper.CategoryMapper;
import com.zwr.fd.service.CategoryService;
import com.zwr.fd.service.DishService;
import com.zwr.fd.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
	@Autowired
	DishService ds;
	
	@Autowired
	SetmealService sts;
	
	@Override
	public void MineRemoveById(Long id) {
		// TODO Auto-generated method stub
		   LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
	        //添加查询条件，根据分类id进行查询
	        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
	        int count1 = (int) ds.count(dishLambdaQueryWrapper);

	        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
	        if(count1 > 0){
	            //已经关联菜品，抛出一个业务异常
	            throw new CustomException("当前分类下关联了菜品，不能删除");
	        }

	        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
	        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
	        //添加查询条件，根据分类id进行查询
	        setmealLambdaQueryWrapper2.eq(Setmeal::getCategoryId,id);
	        int count2 = (int) sts.count(setmealLambdaQueryWrapper2);
	        if(count2 > 0){
	            //已经关联套餐，抛出一个业务异常
	            throw new CustomException("当前分类下关联了套餐，不能删除");
	        }

	        //正常删除分类
	        super.removeById(id);
	}

}
