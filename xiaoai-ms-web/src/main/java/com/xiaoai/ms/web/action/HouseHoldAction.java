package com.xiaoai.ms.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Xiaoilog;
import com.xiaoai.ms.service.IFamilygroupService;
import com.xiaoai.ms.service.IHouseholdService;
import com.xiaoai.ms.service.IXiaoilogService;
import com.xiaoai.ms.vo.HouseHoldVo;
import com.xiaoai.util.FastJsonUtils;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
/**
 * 家电控制器的实现
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Controller("houseHoldAction")
@Scope("prototype")
public class HouseHoldAction extends ActionSupport{
	@Resource(name="houseHoldService")
	private IHouseholdService houseHoldService;
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	@Resource(name="xiaoilogService")
	private IXiaoilogService xiaoilogService;
	
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	private int totalPage;//总页数
	private boolean fals=true;//删除返回的状态标志位
	private Household houseHold;
	private List<Household> houseList;
	/**
	 * 按条件查询
	 * groupName 家庭组名字
	 * id 家庭组id
	 * brand 家电品牌
	 * hhName 家电名字
	 * @return houseList 家电对象集合
	 * @throws IOException io
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public String findAll() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String groupName=request.getParameter("groupName");
		String groupId=request.getParameter("groupId");
		String brand=request.getParameter("brand");
		String hhName=request.getParameter("hhName");
		Household houseHold=new Household();
		if(!XATools.isNull(groupName)){
			Familygroup famin=familyService.getFamilygroupByName(groupName);
			houseHold.setFamilygroup(famin);
		}
		if(!XATools.isNull(groupId)){
			Familygroup famin=familyService.getFamilygroupByid(Integer.parseInt(groupId));
			houseHold.setFamilygroup(famin);
		}
		if(!XATools.isNull(hhName)){
			houseHold.setEaName(hhName);
		}
		
		if(!XATools.isNull(brand)){
			houseHold.setBrand(brand);
		}
		int total=houseHoldService.getCountHouseHold(houseHold);//得到总记录数
		
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		houseList=houseHoldService.findHouseHold(showPage, offset, houseHold);
		
		/*//家电个数图表
				String eaName = null;
				ArrayList<HouseHoldVo> holdVos = new ArrayList<>();
				HouseHoldVo houseHoldVo2 = null;
				for (Iterator iterator = houseList.iterator(); iterator.hasNext();) {
					Household household = (Household) iterator.next();
					eaName = houseHold.getEaName();
					if(StringUtils.isNotBlank(eaName)){
						boolean flag = false;
						HouseHoldVo houseHoldVo = null;
						for (Iterator iterator2 = holdVos.iterator(); iterator2.hasNext();) {
							houseHoldVo = (HouseHoldVo) iterator2.next();
							if(houseHoldVo.getHouseholdName().equals(eaName)){
								houseHoldVo.setCount(houseHoldVo.getCount()+1);
								flag = true;
							}
						}
						if(!flag){
							houseHoldVo2 = new HouseHoldVo();
							houseHoldVo2.setHouseholdName(eaName);
							houseHoldVo2.setCount(1);
							holdVos.add(houseHoldVo2);
						}
					}
				}*/
		request.setAttribute("houseList", houseList);
		/*request.setAttribute("holdVos", holdVos);*/
		return "success";
		
		
	}
	
	/**
	 * 家电数量图表
	 * @return none
	 * @throws IOException io
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public String echart() throws IOException{
		
		List<Household> houseList=houseHoldService.findAllHouseHold();
		//家电个数图表
				String eaName;
				ArrayList<HouseHoldVo> holdVos = new ArrayList<>();
				HouseHoldVo houseHoldVo2;
		for (Household household : houseList) {
			eaName = household.getEaName();
			if (StringUtils.isNotBlank(eaName)) {
				boolean flag = false;
				HouseHoldVo houseHoldVo;
				for (HouseHoldVo holdVo : holdVos) {
					houseHoldVo = holdVo;
					if (houseHoldVo.getHouseholdName().equals(eaName)) {
						houseHoldVo.setCount(houseHoldVo.getCount() + 1);
						flag = true;
					}
				}
				if (!flag) {
					houseHoldVo2 = new HouseHoldVo();
					houseHoldVo2.setHouseholdName(eaName);
					houseHoldVo2.setCount(1);
					holdVos.add(houseHoldVo2);
				}
			}
		}
				//request.setAttribute("holdVos", holdVos);
				String jsonString = FastJsonUtils.toJSONString(holdVos);
				MyRequest.getResponse().println(jsonString);
				return NONE;
	}
	
	/**
	 * 家电品牌图表
	 * @return none
	 * @throws IOException io
	 */
	@SuppressWarnings({ "rawtypes" })
	public String echart2() throws IOException{
		
		List<Household> houseList=houseHoldService.findAllHouseHold();
		//家电个数图表
				String brandName;
				ArrayList<HouseHoldVo> holdVos = new ArrayList<>();
				HouseHoldVo houseHoldVo2;
		for (Household household : houseList) {
			brandName = household.getBrandName();
			if (StringUtils.isBlank(brandName)) {
				String eaName = household.getEaName();
				//brandName = "智能电器"+"【"+eaName+"】";
				brandName = "智能电器" + "【" + eaName + "】";
			}
			boolean flag = false;
			HouseHoldVo houseHoldVo;
			for (HouseHoldVo holdVo : holdVos) {
				houseHoldVo = holdVo;
				if (houseHoldVo.getHouseholdName().equals(brandName)) {
					houseHoldVo.setCount(houseHoldVo.getCount() + 1);
					flag = true;
				}
			}
			if (!flag) {
				houseHoldVo2 = new HouseHoldVo();
				houseHoldVo2.setHouseholdName(brandName);
				houseHoldVo2.setCount(1);
				holdVos.add(houseHoldVo2);
			}
		}
				//request.setAttribute("holdVos", holdVos);
				String jsonString = FastJsonUtils.toJSONString(holdVos);
				MyRequest.getResponse().println(jsonString);
				return NONE;
	}
	/**
	 * 删除
	 * hid 家电id
	 * 
	 * @return fals=true(删除成功)或者fals=false(删除失败)
	 * @throws UnsupportedEncodingException ex
	 */
	public String detelete() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String hid=request.getParameter("hid");
		String se=request.getParameter("se");
		fals=houseHoldService.deleteHousehold(Integer.parseInt(hid));
		if(se.equals("1")){
			return "success";
		}else{
			return "android";
		}
		
	}
	/**
	 * 根据id查询
	 * @return none
	 * @throws UnsupportedEncodingException ex
	 */
	public String selectByid() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String hid=request.getParameter("hid");
		Household house=houseHoldService.selectHouseholdByid(Integer.parseInt(hid));
		Xiaoilog xiaoilog=new Xiaoilog();
		if(house !=null){
				
				xiaoilog.setHousehold(house);
				int total=xiaoilogService.getCountXiaoilog(xiaoilog);//得到总记录数
				page.setTotal(total);
				page.setShowPage(showPage);
				page.setPageNow(pageNow);
				offset=page.getOfferset();//得到开始记录数
				pageNow=page.getpageNow();//得到当前页数
				totalPage=page.gettotalPage();//得到总页数
				List<Xiaoilog> xiaoiLogList=xiaoilogService.selectXiaoilog(xiaoilog, showPage, offset);
				request.setAttribute("logList", xiaoiLogList);
				request.setAttribute("houseHold", house);
				return "success";
		}else{
				
				return "error";
		}
		
		
		
	}
	/**
	 * 修改
	 * hhName 家电名字
	 * groupName 家庭组名字
	 * houseType 家电类型
	 * brand 品牌
	 * model 型号
	 * hid 家电id
	 * @return fals=true(修改成功)或者fals=false(修改失败)
	 * @throws Exception ex
	 */
	public String update() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		//获取页面数据
		
		String eaName=request.getParameter("hhName");
		String groupName=request.getParameter("groupName");
		String classId=request.getParameter("houseType");
		String brand=request.getParameter("brand");
		String model=request.getParameter("model");
		String hid=request.getParameter("hid");
		
		//根据id查询家电实体对象
		Household houseHold=houseHoldService.selectHouseholdByid(Integer.parseInt(hid));
		houseHold.setEaName(eaName);
		//根据家庭组名字得到家庭组实体类
		Familygroup famin=familyService.getFamilygroupByName(groupName);
		houseHold.setFamilygroup(famin);
		houseHold.setClassId(Integer.parseInt(classId));
		houseHold.setBrand(brand);
		houseHold.setModel(model);
		//执行修改持久化操作
		fals=houseHoldService.updateHouseholdByid(famin,houseHold);
		
		
		if(fals){
				out.print("<script>alert('修改成功')</script>");
				out.flush();
				return "success";
		}else{
				out.print("<script>alert('修改失败')</script>");
				out.flush();
				return "error";
		}
		
		
		
	}
	
	
	
	
	
	
	
	public List<Household> getHouseList() {
		return houseList;
	}
	public void setHouseList(List<Household> houseList) {
		this.houseList = houseList;
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
	public Household getHouseHold() {
		return houseHold;
	}
	public void setHouseHold(Household houseHold) {
		this.houseHold = houseHold;
	}
	
	
}
