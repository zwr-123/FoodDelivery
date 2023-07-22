package com.zwr.fd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwr.fd.beans.Setmeal;
import com.zwr.fd.beans.SetmealDish;
import com.zwr.fd.common.CustomException;
import com.zwr.fd.dto.SetmealDto;
import com.zwr.fd.mapper.SetmealMapper;
import com.zwr.fd.service.SetmealDishService;
import com.zwr.fd.service.SetmealService;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{
	 @Autowired
	 private SetmealDishService setmealDishService;
	
	@Override
	@Transactional
	public void saveWithDish(SetmealDto setmealdto) {
		//保存套餐的基本信息，操作setmeal，执行insert操作
		//setmealdto此时没有id 值，似乎执行完save后，把数据库生成的id值，添加进去了
        this.save(setmealdto);
        Long id=setmealdto.getId();
        List<SetmealDish> setmealDishes=setmealdto.getSetmealDishes();
        for (int i = 0; i < setmealDishes.size(); i++) {
        	setmealDishes.get(i).setSetmealId(id);
		}
        
        setmealDishService.saveBatch(setmealDishes);
	}

	
	/**
	 * 删除套餐和其关联的菜品信息
	 */
	@Override
	@Transactional
	public void deleteWithDish(List<Long> ids) {
		   //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        long count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
		
	}

}
