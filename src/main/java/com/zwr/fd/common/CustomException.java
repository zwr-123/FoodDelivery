package com.zwr.fd.common;

/**
 * 自定义业务业异常，用于当餐品分类和 菜品名称，套餐名称有关联时，抛出异常，不能删除
 * @author ZW
 *
 */
public class CustomException extends RuntimeException{
	
	public CustomException(String mess) {
		super(mess);
	}

}
