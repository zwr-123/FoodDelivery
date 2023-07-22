package com.zwr.fd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwr.fd.beans.Category;

public interface CategoryService extends IService<Category>{

	public void MineRemoveById(Long id);

}
