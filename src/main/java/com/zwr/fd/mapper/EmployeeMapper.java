package com.zwr.fd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwr.fd.beans.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
