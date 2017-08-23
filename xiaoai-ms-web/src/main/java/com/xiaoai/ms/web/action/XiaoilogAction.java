package com.xiaoai.ms.web.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.bigData.service.IXiaoiDataForMongoDbService;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.XiaoiDateForMongDb;
import com.xiaoai.entity.Xiaoilog;
import com.xiaoai.ms.service.IFamilygroupService;
import com.xiaoai.ms.service.IUsersService;
import com.xiaoai.ms.service.IXiaoiService;
import com.xiaoai.ms.service.IXiaoilogService;
import com.xiaoai.ms.vo.FamilyVO;
import com.xiaoai.ms.vo.UserVO;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
import com.xiaoleilu.hutool.util.BeanUtil;
@SuppressWarnings("serial")
@Controller("xiaoilogAction")
@Scope("prototype")
public class XiaoilogAction extends ActionSupport{
	
	@Resource(name="xiaoilogService")
	private IXiaoilogService xiaoilogService;
	
	@Resource(name="xiaoiService")
	private IXiaoiService xiaoiService;

	
	@Resource(name="xiaoiDataForMongoDbService")
	private IXiaoiDataForMongoDbService xiaoiDataForMongoDbService;
	
	@Resource(name="familyService")
	private IFamilygroupService familygroupService;
	
	@Resource(name="usersService")
	private IUsersService usersService;
	//线程池
	private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	private static final AtomicInteger THREAD_COUNT = new AtomicInteger(0);
	
	public static ExecutorService getCachedthreadpool() {
		return cachedThreadPool;
	}
	
	private List<Xiaoilog> xiaoilogList;
	
	private Xiaoi xiaoi;
	private String xiaoiNumber;
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	private int totalPage;//总页数
	
	private boolean fals;
	/**
	 * 查询工作日志
	 * @return none
	 * @throws IOException io
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public String select() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		
		String xiaoiNumber=request.getParameter("xiaoiNumber");
		String groupNumber=request.getParameter("groupNumber");
		List<XiaoiDateForMongDb> findByPage;
		
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		Familygroup fa = new Familygroup();
		if(StringUtils.isNoneBlank(xiaoiNumber)||
				StringUtils.isNoneBlank(groupNumber)){
			Map<Object, Object> map = new HashMap<>();
			if(StringUtils.isNoneBlank(xiaoiNumber)&&StringUtils.isBlank(groupNumber)){
				map.put("invokeXiaoNumber", xiaoiNumber);
				xiaoi=xiaoiService.selectXiaoiAllByNumber(xiaoiNumber);
			}else if(StringUtils.isNoneBlank(xiaoiNumber)&&StringUtils.isNoneBlank(groupNumber)){
				map.put("invokeXiaoNumber", xiaoiNumber);
				map.put("invokeGroupNumber", groupNumber);
				xiaoi=xiaoiService.selectXiaoiAllByNumber(xiaoiNumber);
				if(StringUtils.isNumeric(groupNumber)){
					fa = familygroupService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				}
			}else{
				map.put("invokeGroupNumber", groupNumber);
				fa = familygroupService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
			}
			findByPage = xiaoiDataForMongoDbService.findByPage(page,map);
			page.setTotal(findByPage.size());
		}else{
			Long count = xiaoiDataForMongoDbService.getCount();
			page.setTotal(count.intValue());
			findByPage = xiaoiDataForMongoDbService.findByPage(page);
		}
		
		//查询所有家庭组，显示为图表
		List<Familygroup> fList=familygroupService.getAllFamily();
		FamilyVO familyVO = new FamilyVO();
		List<FamilyVO> arrayList = new ArrayList<>();
		String ad = null;
		FamilyVO favo = new FamilyVO();
		for (Iterator iterator = fList.iterator(); iterator.hasNext();) {
			boolean flag = false;
			Familygroup familygroup = (Familygroup) iterator.next();
			String city = familygroup.getCity();
			String district = familygroup.getDistrict();
			if(StringUtils.isNotBlank(city)){
				if(StringUtils.isNotBlank(district)){
					ad = city+district;
				}
			}
			for (FamilyVO vo : arrayList) {
				if(StringUtils.isNotBlank(ad)){
					if(vo.getAddress().equals(ad)){
						vo.setCount(vo.getCount()+1);
						flag = true;
					}
				}
			}
			if(!flag){
				if(StringUtils.isNotBlank(ad)){
					favo=new FamilyVO();
					favo.setAddress(ad);
					favo.setCount(1);
					arrayList.add(favo);
				}
			}
		}
		
		//查询所有用户，显示为图表
		List<Users> users = usersService.getAllUsers();
		
		Users user = new Users();
		String sex;
		List<UserVO> uVos = new ArrayList<>();
		UserVO vo;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			user = (Users) iterator.next();
			sex = user.getUserSex();
			boolean flag = false;
			UserVO userVO;
			String sex2;
			for (Iterator iterator2 = uVos.iterator(); iterator2.hasNext();) {
				userVO = (UserVO) iterator2.next();
				sex2 = userVO.getSex();
				if(sex2.equals(sex)){
					userVO.setCount(userVO.getCount()+1);
					flag = true;
				}
			}
			if(!flag){
				vo = new UserVO();
				vo.setCount(1);
				vo.setSex(sex);
				uVos.add(vo);
			}
		}
		
		request.setAttribute("xiaoilogList",findByPage);
		request.setAttribute("xiaoi",xiaoi);
		request.setAttribute("family", fa);
		request.setAttribute("totalPage", page.gettotalPage());
		request.setAttribute("pageNow", page.getpageNow());
		//家庭组地址图表
		Integer allcount = 0;
		for (FamilyVO familyVO2 : arrayList) {
			allcount+=familyVO2.getCount();
		}
		request.setAttribute("allcount", allcount);
		request.setAttribute("chartList", arrayList);
		
		//性别图表
		Integer userAllCount = 0;
		for (UserVO userVO : uVos) {
			userAllCount+=userVO.getCount();
		}
		request.setAttribute("userAllCount", userAllCount);
		request.setAttribute("userCharList", uVos);
		return "success";
		
	}
	/**
	 * 导出报表
	 * @return none
	 * @throws Exception io
	 */
	@SuppressWarnings({ "unused" })
	public String exportExcel() throws Exception{
		boolean fals=true;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
		response.setCharacterEncoding("utf-8");
		PrintWriter out1 = response.getWriter();
		String xiaoNumber=request.getParameter("xiaoNumber");
		xiaoi=xiaoiService.selectXiaoiByNumber(xiaoNumber);
		if(xiaoi!=null){
		Xiaoilog xiaoilog=new Xiaoilog();
		xiaoilog.setXiaoi(xiaoi);
		HSSFWorkbook workbook =xiaoilogService.exportExecl(xiaoilog);
		//创建一个HttpServletResponse对象 
		response.reset();
		ServletOutputStream  out = response.getOutputStream();//创建一个输出流对象 
		//filename是下载的xls的名
		response.setHeader("Content-disposition","attachment; filename="+"xiaoilog.xls");
		response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
		response.setHeader("Pragma","No-cache");//设置头 
		response.setHeader("Cache-Control","no-cache");//设置头 
		response.setDateHeader("Expires", 0);//设置日期头 
		workbook.write(out); 
		out.flush(); 
		workbook.write(out);
		if(out !=null){
			out.close();
		}
		}else{
			out1.print("<script>alert('没有选择小艾报表信息,无法导出报表')</script>");
			out1.print("<script>window.location.href='showXiaolog.action?=1pageNow&showPage=5'</script>");
			out1.flush(); 
		}
		return NONE;
	}	
	/**
	 * 添加
	 * @return none
	 * @throws IOException io
	 */
	
	public String insertLog() throws IOException{
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = MyRequest.getResponse();
		HttpServletRequest request = MyRequest.getRequest();
		
		final XiaoiDateForMongDb data;
		data = BeanUtil.requestParamToBean(request, XiaoiDateForMongDb.class, true);
		String ip = request.getRemoteAddr();
		data.setInvokeIp(ip);
		//获取参数
		cachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				//设置当前线程名称
				try {
					Thread.currentThread().setName("XiaoilogAction:"+THREAD_COUNT.incrementAndGet());
					data.setInvokeTime(XATools.getTNowTime());
					xiaoiDataForMongoDbService.insertUser(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		jsonObject.put("code", 0);
		out.print(jsonObject);
		return null;
	}
		
	/**
	 * 添加大数据
	 * @return none
	 * @throws IOException io
	 */
	
	public String insertDate() throws IOException{
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = MyRequest.getResponse();
		HttpServletRequest request = MyRequest.getRequest();
		
		final XiaoiDateForMongDb data;
		data = BeanUtil.requestParamToBean(request, XiaoiDateForMongDb.class, true);
		String ip = request.getRemoteAddr();
		data.setInvokeIp(ip);
		//获取参数
		cachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				//设置当前线程名称
				try {
					Thread.currentThread().setName("xiaoai-bigData:"+THREAD_COUNT.incrementAndGet());
					data.setInvokeTime(XATools.getTNowTime());
					xiaoiDataForMongoDbService.insertUser(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		jsonObject.put("code", 0);
		out.print(jsonObject);
		return null;
	}	
	
	public String getXiaoiNumber() {
		return xiaoiNumber;
	}
	public void setXiaoiNumber(String xiaoiNumber) {
		this.xiaoiNumber = xiaoiNumber;
	}
	public List<Xiaoilog> getXiaoilogList() {
		return xiaoilogList;
	}
	public void setXiaoilogList(List<Xiaoilog> xiaoilogList) {
		this.xiaoilogList = xiaoilogList;
	}
	public Xiaoi getXiaoi() {
		return xiaoi;
	}
	public void setXiaoi(Xiaoi xiaoi) {
		this.xiaoi = xiaoi;
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

}
