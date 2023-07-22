package com.zwr.fd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwr.fd.beans.Category;
import com.zwr.fd.beans.Setmeal;
import com.zwr.fd.common.R;
import com.zwr.fd.dto.SetmealDto;
import com.zwr.fd.service.CategoryService;
import com.zwr.fd.service.SetmealDishService;
import com.zwr.fd.service.SetmealService;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
	@Autowired
	SetmealService setmealService;
	
	@Autowired
	SetmealDishService setmealDishService;
	
	@Autowired
	CategoryService categoryservice;
	
	/**
	 * 新增套餐
	 * @param setmealdto
	 * @return
	 */
	@PostMapping
	public R<String> save(@RequestBody SetmealDto setmealdto){
		   setmealService.saveWithDish(setmealdto);
	       return R.success("新增套餐成功");
	}
	
	/**
	 * 套餐分页查询
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@GetMapping("/page")
	public R<Page> page(int page,int pageSize,String name) {
		//套餐分页
		Page<Setmeal> pageinfo=new Page<>(page,pageSize);
		
		//实际要回显的分页信息。
		Page<SetmealDto> SetmealDtoPage = new Page<>();
		
		//构建查询器，并添加查询条件
		LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
		queryWrapper.like(name!=null,Setmeal::getName, name);
		queryWrapper.orderByDesc(Setmeal::getUpdateTime);
		
		//查询
		setmealService.page(pageinfo, queryWrapper);
		
		//复制分页信息
		BeanUtils.copyProperties(pageinfo, SetmealDtoPage, "records");
		
		//复制套餐信息，并查询后填补上套餐分类名称
		List<Setmeal> records=pageinfo.getRecords();
		List<SetmealDto> sdinfo=new ArrayList<>();
		for (int i = 0; i < records.size(); i++) {
			SetmealDto sd=new SetmealDto();
			BeanUtils.copyProperties(records.get(i),sd);
			Long id = records.get(i).getCategoryId();
			Category category = categoryservice.getById(id);
			if(category!=null) {
				sd.setCategoryName(category.getName());
			}
			sdinfo.add(sd);
		}
		SetmealDtoPage.setRecords(sdinfo);
		
		return R.success(SetmealDtoPage);
	}
	
	/**
	 * delete setmeal
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	public R<String> deleteSetmeal(@RequestParam  List<Long> ids){
		setmealService.deleteWithDish(ids);
		return R.success("删除成功");
	}
	
	@GetMapping("/list")
	public R<List<Setmeal>> setmealList(Setmeal setmeal){
		LambdaQueryWrapper<Setmeal> QueryWrapper=new LambdaQueryWrapper<>();
		QueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
		QueryWrapper.eq(setmeal.getStatus()!=null, Setmeal::getStatus,setmeal.getStatus());
		QueryWrapper.orderByDesc(Setmeal::getUpdateTime);
		List<Setmeal> list = setmealService.list(QueryWrapper);
		return R.success(list);
	}
	

}
