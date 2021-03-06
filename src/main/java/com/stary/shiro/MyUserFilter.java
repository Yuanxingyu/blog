package com.stary.shiro;

import com.alibaba.fastjson.JSON;
import com.stary.model.MyResponse;
import com.stary.model.ResponseEnum;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;


public class MyUserFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(MyResponse.createResponse(ResponseEnum.NON_LOGIN)));
        return false;
    }
}
