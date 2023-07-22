package com.zwr.fd.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson2.JSON;
import com.zwr.fd.common.R;
import com.zwr.fd.common.autoFillUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1、获取本次请求的URI 为什么获取的是uri
        String requestURI = request.getRequestURI();// /backend/index.html

        log.info("拦截到请求：{}",requestURI);

        //定义不需要处理的请求路径  注意：/backend/** 与 /backend/index.html 匹配，返回false 为什么？
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };


        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4、判断网页登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee") != null){
        	Long id=(Long)request.getSession().getAttribute("employee");
            log.info("用户已登录，用户id为：{}",id);
            autoFillUtil.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }
        
        
        //4-2、判断手机登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            Long userId = (Long) request.getSession().getAttribute("user");
            autoFillUtil.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }
        

        log.info("用户未登录");
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
