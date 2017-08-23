package com.xiaoai.ms.web.action;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoai.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Manufacturer;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.Xiaoimac;
import com.xiaoai.ms.service.IFamilygroupService;
import com.xiaoai.ms.service.IManufacturerService;
import com.xiaoai.ms.service.IXiaoiMacService;
import com.xiaoai.ms.service.IXiaoiService;

@SuppressWarnings("serial")
@Controller("xiaoiAction")//控制层 都要写 固定写法
@Scope("prototype")
public class XiaoiAction extends ActionSupport{
	
	private String familynumber;//家庭组编号
	private int id;
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	
	private int totalPage;//总页数
	private boolean fals;//删除返回的状态标志位
	@Resource(name="xiaoiService")
	private IXiaoiService xiaoiService;
	
	
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	
	@Resource(name="xiaoiMacService")
	private IXiaoiMacService xiaoiMacService;
	
	@Resource(name="manufacturerService")
	private IManufacturerService manufacturerService;
	
	/**
	 * 二维码宽
	 */
	@Value("${QR_WIDTH}")
	private Integer QR_WIDTH;
	
	/**
	 * 二维码高
	 */
	@Value("${QR_HEIGHT}")
	private Integer QR_HEIGHT;
	
	/**
	 * 二维码宽
	 */
	@Value("${QR_FORMAT}")
	private String QR_FORMAT;
	/**
	 * 根据编号查询
	 * xiaoiNumber 终端编号
	 * @return none
	 * @throws IOException io
	 */
	
	@SuppressWarnings("unused")
	public String getXiaoi() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoiNumber=request.getParameter("xiaoiNumber");
		Xiaoi xi=xiaoiService.selectXiaoiByNumber(xiaoiNumber);
		int num=100;
		boolean fals=true;
		if(xi !=null){//数据库中存在此编号
			if(xi.getState()==0){//如果未使用
				for(int i=0;i<num;i++){
					familynumber=JavaDemo10.getRandomNumber();//得到家庭组编号
					try {
						Familygroup fg=familyService.getFamilygroupByNumber(Integer.parseInt(xiaoiNumber));
					} catch (Exception e) {
						fals=false;	
					}
					if(!fals){//验证该编号是否已使用
					}else{//未使用
						break;
					}
				}
				return "success";
			}
			else{//该小艾已使用
				return "error";
			}
		}else{//该编号不存在
			
			return "error";
		}
	}
	
	/**
	 * 按条件查询
	 * xiaoNumber 终端小艾编号、
	 * groupName 家庭组名字
	 * @return list(终端对象集合)，pageNow(页面显示当前页),totalPage(总页数)
	 * @throws IOException io
	 */
	public String findAll() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=response.getWriter();
		String xiaoNumber=request.getParameter("xiaoNumber");	
		String groupName=request.getParameter("groupName");
		Xiaoi xiao=new Xiaoi();
		if(xiaoNumber !=null && ! xiaoNumber.equals("")){
			xiao.setXiaoNumber(xiaoNumber); 
		}
		
		if(groupName !=null &&! "".equals(groupName)){
			Familygroup fg=familyService.getFamilygroupByName(groupName);
			xiao.setFamilygroup(fg);
			
		}
		int total=xiaoiService.getXiaoiCount(xiao);//得到总记录数
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		List<Xiaoi> xiaoList=xiaoiService.findAll(offset, showPage, xiao);
		if(xiaoList.size()>0){
			request.setAttribute("list", xiaoList);
			return "success";
		}else{
		//	out.print("<script>alert('对不起，您查询的小艾不存在')</script>");
		//	out.print("<script>window.location.href='findAllXiaoi.action?pageNow=1&showPage=5'</script>");
			out.flush();
			return "error";
		}
		
	}
	
	
	
	
	/**
	 * 删除
	 * id
	 * @return none
	 * @throws IOException io
	 */
	public String deleteXiaoi() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Xiaoi xi=new Xiaoi();
		xi.setXid(id);
		boolean fals=xiaoiService.delete(xi);
		if(fals){
			out.print("<script>alert('删除成功')</script>");
			out.flush(); 
			return "success";
		}else{
			out.print("<script>alert('删除失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 根据id查询
	 * id 终端小艾ID
	 * @return none
	 * @throws UnsupportedEncodingException ex
	 */
	public String getXiaoiByid() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		Xiaoi xiao=xiaoiService.getXiaoiByid(id);
		request.setAttribute("xiao", xiao);
		return "success";
	}
	
	/**
	 * 修改
	 * id 终端小艾ID
	 * xiaoName 终端名字
	 * xiaoType 终端类型
	 * groupName 家庭组名字
	 * useType 使用状态
	 * @return none
	 * @throws IOException io
	 */
	public String updateXiaoi() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoName=request.getParameter("xiaoName");
		String xiaoType=request.getParameter("xiaoType");
		String groupName=request.getParameter("groupName");
		Familygroup family=familyService.getFamilygroupByName(groupName);
		String usesType=request.getParameter("useType");
		int useType=Integer.parseInt(usesType);	
		Xiaoi xiao=xiaoiService.getXiaoiByid(id);
		xiao.setXname(xiaoName);
		xiao.setXiaoType(Integer.parseInt(xiaoType));
		xiao.setFamilygroup(family);
		xiao.setState(useType);
		boolean fals=xiaoiService.updateXiaoi(xiao);
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('修改成功')</script>");
			out.flush();
			return "success";
		}else{
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('修改失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 添加
	 * 
	 * xiaoName 终端名字
	 * xiaoType 终端类型
	 * groupName 家庭组名字
	 * useType 使用状态
	 * @return none
	 * @throws IOException io
	 */
	public String addXiaoai() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoName=request.getParameter("xiaoName");
		String xiaoNumber=request.getParameter("xiaoNumber");
		String xiaoType=request.getParameter("xiaoType");
		Xiaoi xiao=new Xiaoi();
		xiao.setXname(xiaoName);
		xiao.setXiaoNumber(xiaoNumber);
		if(!XATools.isNull(xiaoType)){
		xiao.setXiaoType(Integer.parseInt(xiaoType));
		}
		xiao.setState(0);
		fals=xiaoiService.insertXiaoi(xiao);
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('添加成功')</script>");
			out.flush();
			return "success";
		}else{
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('添加失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	
	/**
	 * 初始化MAC地址前置准备方法
	 * @return none
	 * @throws Exception ex
	 */
	public String preInitMac() throws Exception{
		HttpServletRequest req = MyRequest.getRequest();
		
		total = xiaoiMacService.getXiaoiMacCount();
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		
		List<Xiaoimac> list=xiaoiMacService.getAll(offset, showPage);
		
		//查询厂商
		List<Manufacturer> manufacturers = manufacturerService.getAll();
		
		req.setAttribute("macList", list);
		req.setAttribute("manufacturers", manufacturers);
		
		return SUCCESS;
	}
	
	/**
	 * 初始化MAC地址
	 * @return none
	 * @throws Exception io
	 */
	public String initXiaoiMac() throws Exception{
		
		HttpServletRequest request = MyRequest.getRequest();
		HttpServletResponse resp = MyRequest.getHttpResponse();
		//String byte1 = request.getParameter("BYTE1");
		String[] parameterValues = request.getParameterValues("BYTE1");
		Integer byte1 = 0;
		for (String string : parameterValues) {
			//参数判断
			if(!StringUtils.isNumeric(string)){
				out(resp, "BYTE1只能为数字！", "xiaoai-ms-web/jsp/xiaoi_mac.jsp");
				return NONE;
			}
			byte1+=Integer.parseInt(string);
		}
		String byte7 = request.getParameter("BYTE7");
		String manufacturer = request.getParameter("manufacturer");
		
		//参数判断
		if(!StringUtils.isNumeric(byte7)){
			out(resp, "BYTE7只能为数字！", "xiaoai-ms-web/jsp/xiaoi_mac.jsp");
			return NONE;
		}
		if(!byte7.equals("1")&&!byte7.equals("2")){
			out(resp, "BYTE7只能为1或者2", "xiaoai-ms-web/jsp/xiaoi_mac.jsp");
			return NONE;
		}
		if(StringUtils.isBlank(manufacturer)){
			out(resp, "厂商名称不能为空！", "xiaoai-ms-web/jsp/xiaoi_mac.jsp");
			return NONE;
		}
		
		//查询厂商表
		Manufacturer manu = manufacturerService.selectByName(manufacturer);
		//查询所有厂商
		List<Manufacturer> manufacturers = manufacturerService.getAll();
		//查询当前厂商下的终端数量,作为当前新增终端的依据
		Integer amount = xiaoiMacService.selectAmount(manu);
		Xiaoimac xiaoimac;
		amount++;
		if(manu!=null){
			xiaoimac = xiaoiMacService.insertXiaoiMac(manu.getId(),amount,String.valueOf(byte1 ), byte7);
		}else{
			out(resp, "当前厂商尚未注册，请注册后重试！", "xiaoai-ms-web/jsp/xiaoi_mac.jsp");
			return NONE;
		}
		
		//BASE64加密
		String encode = Base64Utils.encode(xiaoimac.getMac().getBytes());
		
		//生成二维码
		String filePath = QRCode.generateQRCode(encode, QR_WIDTH, QR_HEIGHT, QR_FORMAT);

		//生成device.properties文件
		String fileName = "device.i.properties";
		File file = new File(fileName);
		String path = null;
		try {
			file.createNewFile();
			//写内容
			FileWriter fileWriter = new FileWriter("device.i.properties");
			fileWriter.write(xiaoimac.getMac());
			fileWriter.flush();
			fileWriter.close();

			//2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:FastDFS/client.conf");
			//声明文件输入流，为输入流指定文件路径

			byte[] buff = IOUtils.toByteArray(new FileInputStream("device.i.properties"));
			//3、执行上传处理
			path = fastDFSClient.uploadFile(buff,"properties");
			//4、拼接返回的url和ip地址，拼装成完整的url
			path= "http://42.159.249.229/" + path;
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//更新数据库url
		xiaoimac.setUploadurl(filePath);
		xiaoiMacService.updateUrl(xiaoimac);

		request.setAttribute("filePath", filePath);
		request.setAttribute("macAddress", path);
		
		List<Xiaoimac> arrayList = new ArrayList<>();
		arrayList.add(xiaoimac);
		request.setAttribute("macList", arrayList);
		request.setAttribute("manufacturers", manufacturers);
		return "success";
	}

	
	public String getFamilynumber() {
		return familynumber;
	}
	public void setFamilynumber(String familynumber) {
		this.familynumber = familynumber;
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

	public boolean isFals() {
		return fals;
	}

	public void setFals(boolean fals) {
		this.fals = fals;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * 输出错误信息方法
	 * resp
	 * @param message 错误信息
	 * @param path 跳转路径
	 * @throws IOException io
	 */
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
					+ "window.location.href='/"+path
					+ "'</script>");
		}
	}
}
