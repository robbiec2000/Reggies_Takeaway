package com.rc.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.rc.reggie.common.BaseContext;
import com.rc.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        log.info(requestURI);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        for(String url: urls){
            if(PATH_MATCHER.match(url, requestURI)){
                log.info("Request not handling");
                filterChain.doFilter(request,response);
                return;
            }
        }

        if(request.getSession().getAttribute("employee") != null){
            long empId = (long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            log.info("Staff login: {}", empId);
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("user") != null){
            long userId = (long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            log.info("Login user: {}", userId);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("user not login");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
}
