package com.stary.shiro;

import com.alibaba.fastjson.JSON;
import com.stary.model.MyResponse;
import com.stary.model.ResponseEnum;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MyRolesFilter extends RolesAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (subject.getPrincipal() == null) {
            out.write(JSON.toJSONString(MyResponse.createResponse(ResponseEnum.NON_LOGIN)));
        }
//        } else {
//            out.write(JSON.toJSONString(MyResponse.createResponse((ResponseEnum.NON_PERM))));
//        }
        return false;
    }

}
