package com.zwr.fd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwr.fd.beans.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
