package com.zwr.fd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwr.fd.beans.Category;
import com.zwr.fd.beans.Dish;
import com.zwr.fd.beans.DishFlavor;
import com.zwr.fd.common.R;
import com.zwr.fd.dto.DishDto;
import com.zwr.fd.service.CategoryService;
import com.zwr.fd.service.DishFlavorService;
import com.zwr.fd.service.DishService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
	@Autowired
	private  DishService ds;
	
	@Autowired
	private DishFlavorService dfs;
	
	@Autowired
	private CategoryService catS;
	
	/**
	 * 添加菜品
	 * @param dishDto
	 * @return
	 */
	@PostMapping
	public R<String> Save(@RequestBody DishDto dishDto){
		ds.saveWithFlavor(dishDto);
		return R.success("新增菜品成功");
	}
	
	/**
	 * 菜品页，分页回显
	 * @param page
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@GetMapping("/page")
	public R<Page> page(int page,int pageSize,String name){
		Page<Dish> pageinfo=new Page<>(page,pageSize);
		//实际要回显的分页信息。
		Page<DishDto> dishDtoPage = new Page<>();
		
		LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>(); 
		queryWrapper.like(name!=null,Dish::getName, name);
		queryWrapper.orderByDesc(Dish::getUpdateTime);
		ds.page(pageinfo, queryWrapper);
		
		//只拷贝分页信息，排除掉分页中的list即菜品信息
		BeanUtils.copyProperties(pageinfo, dishDtoPage, "records");
		
		//循环复制每一个菜品信息,并添加菜品名到实际分页。
		List<Dish> records = pageinfo.getRecords();
		List<DishDto> Decords=new ArrayList<>();
		for (int i = 0; i < records.size(); i++) {
			DishDto dishDto = new DishDto();
			//复制菜品信息
			BeanUtils.copyProperties(records.get(i),dishDto);
			
			//根据id查询菜品名,再赋值给dishDto
			Long categoryId = records.get(i).getCategoryId();
			Category byId = catS.getById(categoryId);
			if(byId!=null) {
				dishDto.setCategoryName(byId.getName());
			}
			Decords.add(dishDto);
		}
		dishDtoPage.setRecords(Decords);
		return R.success(dishDtoPage);
	}
	
    /**
     * 根据id查询菜品信息和对应的口味信息(回显修改菜品页需要的信息)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = ds.getByIdWithFlavor(id);

        return R.success(dishDto);
    }
    
    
    
    @PutMapping
	public R<String> updataDishDto(@RequestBody DishDto dishDto){
		ds.updateWithFlavor(dishDto);
		return R.success("修改菜品成功");
	}
    
    
    /**
     * 根据条件查询并回显菜品信息到 套餐管理 下的 添加菜品 
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = ds.list(queryWrapper);
        List<DishDto> DtoList=new ArrayList<>();
        
        for (int i = 0; i < list.size(); i++) {
			DishDto dishDto = new DishDto();
			//复制菜品信息
			BeanUtils.copyProperties(list.get(i),dishDto);
			
			//根据id查询菜品名,再赋值给dishDto
			Long categoryId = list.get(i).getCategoryId();
			Category byId = catS.getById(categoryId);
			if(byId!=null) {
				dishDto.setCategoryName(byId.getName());
			}
			
			//根据id查询口味
			Long DishId = list.get(i).getId();
			LambdaQueryWrapper<DishFlavor> queryWrapper2 = new LambdaQueryWrapper<>();
	        queryWrapper2.eq(DishFlavor::getDishId,DishId);
	        List<DishFlavor> dishFlavorList = dfs.list(queryWrapper2);
	        dishDto.setFlavors(dishFlavorList);
	        DtoList.add(dishDto);
		}
        

        return R.success(DtoList);
    }

}
