package com.zwr.fd.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zwr.fd.common.JacksonObjectMapper;

@Configuration
public class myConfig extends WebMvcConfigurationSupport{
	
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	@Bean
	public DruidDataSource dataSource() {
		return new DruidDataSource();
	}
	
	//static content map
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
		registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
	}
	
	//page interceptor
	@Bean
	public MybatisPlusInterceptor mpinterceptor() {
		MybatisPlusInterceptor inter = new MybatisPlusInterceptor();
		inter.addInnerInterceptor(new PaginationInnerInterceptor());
		return inter;
	}
	
	//test 6.29
	//添加消息转化器，处理 返回的id 日期等
	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		super.extendMessageConverters(converters);
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
		// 0 表示放到list集合第一位，即优先级第一
		converters.add(0,jackson2HttpMessageConverter);
		
	}
	
}
