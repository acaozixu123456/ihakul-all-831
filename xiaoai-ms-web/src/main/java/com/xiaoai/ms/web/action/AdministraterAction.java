package com.xiaoai.ms.web.action;



import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Administrate;
import com.xiaoai.entity.Privilege;
import com.xiaoai.entity.Role;
import com.xiaoai.ms.redis.JedisClient;
import com.xiaoai.ms.service.IAdministraterService;
import com.xiaoai.ms.service.IPrivilegeService;
import com.xiaoai.ms.service.IRoleService;
import com.xiaoai.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
/**
 *      
 * 类名称：AdministraterAction    
 * 类描述：管理员控制器的实现 
 * 创建人：蓝道优    
 * 创建时间：2016-12-6 下午11:07:43    
 * 修改人：   
 * 修改时间：   
 * 修改备注：    
 */


@Controller("adminAction")//控制层 都要写 固定写法
@Scope("prototype")
public class AdministraterAction extends ActionSupport{
	
	/**
	 * aa
	 */
	private static final long serialVersionUID = 1L;
	private String aname;
	private String password;
	private int aid;
	
	private boolean fals;
	private List<Administrate> adminList;
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	private int totalPage;//总页数
	@Resource(name="adminService")//注入类  
	private IAdministraterService adminService;
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="privilegeService")
	private IPrivilegeService privilegeService;
	
	@Resource
	JedisClient jedisClient;
	/**
	 * sso登录过期时间
	 */
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	
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
	
	@Value("${COOKIE_EXPIRE_TIME}")
	private Integer COOKIE_EXPIRE_TIME;
	
	private static Logger logger = Logger.getLogger(AdministraterAction.class);
	/**
	 * 管理员登入
	 * aname 管理员用户名
	 * password 管理员用户密码
	 * @return fals=true(登入成功)或者fals=false(登入失败)
	 * @throws IOException 
	*/ 
	
	public String adminLogin() throws IOException{
		logger.info("管理员登录！");
		//初始化
		HttpServletRequest request = MyRequest.getRequest();
		HttpServletResponse response = MyRequest.getHttpResponse();
		PrintWriter out = MyRequest.getResponse();
		//JSONObject jsonObject = new JSONObject();
		
		
		Administrate admin= adminService.selectAdmiByad(aname);
		String result = null;
		if(admin!=null){ //如果有该用户
		if(password.equals(admin.getPassword())){ //和密码正确
			//保存登入后的管理员信息
			//存入redis，单点登录
			String token = UUID.randomUUID().toString();
			jedisClient.hset(ADMIN_INFO+":"+token, token, FastJsonUtils.toJSONString(admin));
			//session.setAttribute("admin", admin);
			int loginTimes=admin.getLoginNumber();//得到之前登入次数
			loginTimes++;
			admin.setLoginNumber(loginTimes);
			admin.setLoginLastTime(XATools.getNowTime());
			adminService.updateAdmin(admin);//修改最后登入时间和登入次数
			int id=admin.getRole().getRid();
			List<Privilege> listPri=privilegeService.findPrivilegeByid(id);	
			jedisClient.hset(ADMIN_INFO+":"+token, token+"privilege", FastJsonUtils.toJSONString(listPri));
			//session.setAttribute("privilege", listPri);
			//设置过期时间
			jedisClient.expire(ADMIN_INFO+":"+token, EXPIRE_TIME);
			//把token写入cookie,返回浏览器cookie
			CookieUtils.setCookie(request, response, COOKIE_NAME, token,COOKIE_EXPIRE_TIME);
			
			result = XiaoiResult.okByJson("登录成功！");
			logger.info("admin登录结果:"+result);
			out.print(result);
			//out.print("123");
			return null;
			//return SUCCESS;
		}else{
			result=XiaoiResult.buildByJson("用户名密码不匹配", 1);
			out.print(result);
			//out.print("456");
			logger.info("admin登录结果:"+result);
			return null;
		}
		}else{
			result=XiaoiResult.buildByJson("没有该管理员", 1);
			out.print(result);
			//out.print("789");
			logger.info("admin登录结果:"+result);
			return null;
			//return null;
		}
		
	}
	
	
	/**
	 * 分页查询所有管理员
	 * pageNow 页面显示当前页
	 * showPage 页面显示最大记录数
	 * @return adminList(管理员集合),pageNow页面显示当前页,totalPage总页数
	 * @throws IOException 
	 */
	
	@SuppressWarnings("unused")
	public String selectAlladmin() throws IOException {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int total=adminService.getCountAdmin();//得到总记录数
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		adminList=adminService.selectAll(offset, showPage);
		request.setAttribute("adminList", adminList);
		return "success";
	}
	
	/**
	 * 根据id查询管理员
	 * aid 管理员id
	 * @return admin 管理员对象
	 * @throws UnsupportedEncodingException 
	 */
	
	public String selectAdminByid() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		request.getAttribute("aid");
		Administrate admin=adminService.selectAdminByid(aid);
		request.setAttribute("admin", admin);
		return "success"; 
	}
	
	
	/**
	 * 根据用户名查询管理员
	 * aname 管理员名称
	 * @return admin 管理员对象
	 * @throws IOException 
	 */

	//<editor-fold desc="selectAdminByan">
	public String selectAdminByan() throws IOException{
		JSONObject json=new JSONObject();
		boolean fals=false;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
	    String aname=request.getParameter("aname");
		Administrate admin=adminService.selectAdmiByad(aname);
		if(admin!=null){
			fals=true;
		}
		json.put("fals", fals);
		out.print(json.toString());
		return null; 
	}
	//</editor-fold>
	
	/**
	 * 修改
	 * aid 管理员id
	 * realName 管理员真实姓名
	 * phoneNumber 管理员手机号
	 * roleName 管理员权限名
	 * sex 管理员性别
	 * aname 用户名
	 * password 密码
	 * @return fals=true(修改成功)或者fals=false(修改失败)
	 * @throws IOException io
	 */
	@SuppressWarnings("unused")
	public String updateAdminByid() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String realName=request.getParameter("realName");
		String phoneNumber=request.getParameter("phoneNumber");
		String roleName=request.getParameter("roleName");
		String sex=request.getParameter("sex");
		Role role=roleService.selectRoleByname(roleName);
		Administrate admin=adminService.selectAdminByid(aid);
		admin.setAname(aname);
		admin.setPassword(password);
		admin.setRole(role);
		admin.setRealName(realName);
		admin.setPhoneNumber(phoneNumber);
		admin.setSex(sex);
		fals=adminService.updateAdmin(admin);//执行持久化操作返回状态标志位 
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			//out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('修改成功')</script>"); 
			//out.flush();
			return "success";
		}else{
			//out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('修改失败')</script>"); 
			//out.flush();
			return "error";
		}
		
		
	}
	
	
	/**
	 * 添加
	 * realName 管理员真实姓名
	 * phoneNumber 管理员手机号
	 * roleName 管理员权限名
	 * sex 管理员性别
	 * aname 用户名
	 * password 密码
	 * @return fals=true(添加成功)或者fals=false(添加失败)
	 * @throws IOException io
	 */
	@SuppressWarnings("unused")
	public String insertAdmin() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String aname=request.getParameter("aname");
		String password=request.getParameter("password");
		String realName=request.getParameter("realName");
		String phoneNumber=request.getParameter("phoneNumber");
		String sex=request.getParameter("sex");
		Role role=roleService.selectRoleByid(2); //默认设置为普通管理员
		if(role==null){
			//第一次启动服务器，还未设置角色
			
		}
		Administrate admin=new Administrate();
		admin.setAname(aname);
		admin.setPassword(password);
		admin.setPhoneNumber(phoneNumber);
		admin.setSex(sex);
		admin.setRealName(realName);
		admin.setLoginNumber(0);
		admin.setRole(role);
		boolean fals=adminService.insertAdmin(admin);
		if(fals){//添加持久化操作返回真
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			//out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('添加成功')</script>"); 
			//out.flush();
			return "success";
		}else{//添加持久化操作返回假
			//out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('添加失败')</script>");  
			//out.flush();
			return "error";
		}
		
	}
	/**
	 * 删除
	 * aid 管理员id
	 * @return fals=true(删除成功)或者fals=false(删除失败)
	 * @throws IOException 
	 * 
	 */
	public String deleteAdmin() throws IOException {
		JSONObject json=new JSONObject();
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		@SuppressWarnings("unused")
		PrintWriter out = response.getWriter();
		String aid=request.getParameter("aid");
		//根据id查找管理员
		Administrate admin=adminService.selectAdminByid(Integer.parseInt(aid));
		//执行删除持久化
		fals=adminService.deleteAdminByid(admin);
		int refreshNumber=1;
		request.setAttribute("refresh", refreshNumber);
		json.put("fals", fals);
		//out.print(json.toString()); 
		//out.flush();
		return "success";
		
	
		
	}

	public List<Administrate> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Administrate> adminList) {
		this.adminList = adminList;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	
	public boolean isFals() {
		return fals;
	}
	
	public void setFals(boolean fals) {
		this.fals = fals;
	}


	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}


	public int getPageNow() {
		return pageNow;
	}


	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}


	public int getShowPage() {
		return showPage;
	}


	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}


	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	
	/**
	 * 输出错误信息方法
	 * @param resp
	 * @param message 错误信息
	 * @param path 跳转路径
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void out(HttpServletResponse resp,String message,String path) throws IOException {
		resp.setContentType("text/html;charset=utf-8");
		//alert弹框
		resp.getWriter().println("<html><head><meta charset='UTF-8'></head>"
				+"<script language='javascript'>alert('"+
				message+"')</script></html>");
		//页面跳转
		if(StringUtils.isNotBlank(path)){
			resp.getWriter().println(""
					+ "<script>"
					+ "window.location.href='/ihakul_server/"+path
					+ "'</script>");
		}
	}
}
