package com.zwr.fd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zwr.fd.beans.Dish;
import com.zwr.fd.beans.Employee;
import com.zwr.fd.dto.DishDto;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
class FoodDeliveryApplicationTests {
	
	@Test
	void contextLoads() {
		DishDto d=new  DishDto();
		d.setDescription("你好");
		System.out.println(d);
	}

}
