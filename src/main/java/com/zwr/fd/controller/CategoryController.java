package com.zwr.fd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwr.fd.beans.Category;
import com.zwr.fd.common.R;
import com.zwr.fd.service.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	/*
	 * 新增套餐分类  菜品分类似乎没写，也是在这个方法里
	 */
	@PostMapping
	public R<String> save(@RequestBody Category category){
		categoryService.save(category);
		return R.success("新增分类成功");
	}
	
	/*
	 * 套餐分类查询
	 */
	@GetMapping("/page")
	public R<Page> page(int page,int pageSize){
		//构建分页对象
		Page p1 = new Page(page, pageSize);
		
		//创建查询器
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		//给查询器设置查询条件
		queryWrapper.orderByAsc(Category::getSort);
		
		//执行查询
		categoryService.page(p1, queryWrapper);
		return R.success(p1);
	}
	
	
	/**
	 * 删除菜品分类
	 * @param id
	 * @return
	 */
	@DeleteMapping
	public R<String> delete(Long ids){
		categoryService.MineRemoveById(ids);
		return R.success("分类信息删除成功");
	}
	
	
	
	 /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }
    
    
    /**
     * 根据条件查询分类数据，用于添加菜品时，回显菜品分类到下拉列表
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
	
	

}
