package com.xiaoai.ms.web.interceptors;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.xiaoai.ms.redis.JedisClient;
import com.xiaoai.util.ContextHolder;
import com.xiaoai.util.CookieUtils;
/**
 * 管理员权限拦截器
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class AdminInterceptor extends MethodFilterInterceptor{

	/**
	 * redis中保存的key
	 */
	@Value("${ADMIN_INFO}")
	private String ADMIN_INFO;
	
	/**
	 * cookie名称
	 */
	@Value("${COOKIE_NAME}")
	private String COOKIE_NAME;
	
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		  HttpServletRequest request=ServletActionContext.getRequest();
		  HttpServletResponse response=ServletActionContext.getResponse();
		  request.setCharacterEncoding("UTF-8");
		  response.setCharacterEncoding("UTF-8");
		  PrintWriter out=response.getWriter();
		  String result="";
		  
		  
		  JedisClient jedisClient = ContextHolder.getBean(JedisClient.class);
			String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
			String hget = jedisClient.hget(ADMIN_INFO+":"+token, token+"privilege");

			if(StringUtils.isNotBlank(hget)){
				result=invocation.invoke();  //执行Action方法  
				  return result;
			}else{
				out.print("<script>alert('你是普通管理员，你没有此权限')</script>");
				  out.print("<script>alert(window.location.href('main.jsp'))</script>");
				  out.flush(); 
				return "error";
			}
	}
	
}
