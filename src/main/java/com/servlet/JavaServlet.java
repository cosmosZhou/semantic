package com.servlet;

import javax.servlet.*;
import java.io.IOException;

public class JavaServlet implements Servlet {
	public void init(ServletConfig config) throws ServletException {

		System.out.println("servlet initialization");
	}

	public ServletConfig getServletConfig() {
		System.out.println("getServletConfig");
		return null;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		System.out.println("service");
		response.getOutputStream().write("<font color='red'>Java Servlet</font>".getBytes());
	}

	public String getServletInfo() {
		System.out.println("getServletInfo方法调用。。。");
		return null;
	}

	public void destroy() {
		System.out.println("destroy销毁实例。。。");
	}
}