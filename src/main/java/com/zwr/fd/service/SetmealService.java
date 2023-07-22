package com.zwr.fd.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwr.fd.beans.Setmeal;
import com.zwr.fd.dto.SetmealDto;

public interface SetmealService extends IService<Setmeal>{

	public void saveWithDish(SetmealDto setmealdto);
	
	/**
	 * 删除套餐和其关联的菜品信息
	 * @param ids
	 */
	public void deleteWithDish(List<Long> ids);

}
