package com.zwr.fd.common;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class myMetahandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		metaObject.setValue("createTime", LocalDateTime.now());
		metaObject.setValue("createUser", autoFillUtil.getCurrentId());
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("updateUser", autoFillUtil.getCurrentId());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("updateUser", autoFillUtil.getCurrentId());
		
	}

}
