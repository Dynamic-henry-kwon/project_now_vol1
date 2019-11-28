package com.timeIsGold.appNow.User.Service.Security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


public class SigninSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws ServletException, IOException {
		String accept = request.getHeader("accept");
		String nav = request.getHeader("nav");
		if(StringUtils.indexOf(accept, "html") > -1) {
			super.onAuthenticationSuccess(request, response, auth);
		}else if(StringUtils.indexOf(accept, "json") > -1) {
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				String data = StringUtils.join(new String[] { " { \"response\" : {", " \"error\" : false , ", " \"message\" : \"로그인하였습니다.\" , ", " \"nav\" : \"" + nav + "\"" , "} } " });
				PrintWriter out = response.getWriter();
				out.print(data);
				out.flush();
				out.close();
		}
	}
}
