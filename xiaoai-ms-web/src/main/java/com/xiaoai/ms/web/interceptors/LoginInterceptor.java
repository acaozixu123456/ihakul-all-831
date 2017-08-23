package com.xiaoai.ms.web.interceptors;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xiaoai.ms.redis.JedisClient;
import com.xiaoai.util.ContextHolder;
import com.xiaoai.util.CookieUtils;
/**
 * 登入拦截器
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

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
	
	/**
	 * 过期时间
	 */
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	
	public String intercept(ActionInvocation invocation) throws Exception {
	     //取得请求相关的ActionContext实例    
          
        HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JedisClient jedisClient = ContextHolder.getBean(JedisClient.class);
		String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        //如果没有登陆，都返回重新登陆 
        if (StringUtils.isNotBlank(token))    
        {    
        	String hget = jedisClient.hget(ADMIN_INFO+":"+token, token);
        	if(StringUtils.isNotBlank(hget)){
        		//设置过期时间
    			jedisClient.expire(ADMIN_INFO+":"+token, EXPIRE_TIME);
        		return invocation.invoke();    
        	}else{
        		//out.print("<script>alert(window.location.href('login.jsp'))</script>");
  			  	//out.flush(); 
  			  	out(response, "登录已过期，请重新登录！", "jsp/login.jsp");
  			  	//return "error";
  			  	return null;
  			  	
        	}
        }else{
        	 
			  //out.print("<script>alert(window.location.href('login.jsp'))</script>");
			// out.flush(); 
			  //return "error";
        	out(response, "登录已过期，请重新登录！", "jsp/login.jsp");
        	return null;
        }    
       
            
       
       

		
	}
	
	/**
	 * 输出错误信息方法
	 * @param resp
	 * @param message 错误信息
	 * @param path 跳转路径
	 * @return
	 * @throws IOException
	 */
	private void out(HttpServletResponse resp,String message,String path) throws IOException {
		ActionContext ac = ActionContext.getContext();   
        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);   
        String realPath = sc.getRealPath("/");
        
		resp.setContentType("text/html;charset=utf-8");
		//alert弹框
		resp.getWriter().println("<html><head><meta charset='UTF-8'></head>"
				+"<script language='javascript'>alert('"+
				message+"')</script></html>");
		//页面跳转
		if(StringUtils.isNotBlank(path)){
			resp.getWriter().println(""
					+ "<script>"
					+ "window.location.href='+/"+path
					+ "'</script>");
		}
	}

}
