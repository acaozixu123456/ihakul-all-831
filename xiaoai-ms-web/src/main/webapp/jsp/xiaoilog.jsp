<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <meta charset="UTF-8">
	<title>机器人小艾后台管理系统-日志管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
	
	<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jsapi.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/corechart.js"></script>		
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.gvChart-1.0.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ba-resize.min.js"></script>
	
	
</head>
  </head>
  <div class="main_title"><i></i><a href="">小艾</a>><a href="javascript:void(0)"><strong>数据管理</strong></a></div>
  <body>


<div class="container">
	<form action="${pageContext.request.contextPath }/showXiaolog.action?showPage=10&pageNow=1" method="post">
	<ul class="ad_list_search clearfix">
		<li class="time_from"><label>小艾编号：</label><input type="text" name="xiaoiNumber" id="xiaoinu" value="${xiaoi.xiaoNumber }"></li>
		<li class="time_from"><label>家庭组编号：</label><input type="text" name="groupNumber" id="groupNumber" value="${family.groupNumber }"></li>
		<li class="search_btn"><button id="search_bt">搜索</button></li>
	</ul>
	</form>
		<table class="table mt_20 p_lr_15 table_2">
				
				<tr>
					<td width="150"><i class="red_heart"></i>小艾编号：${xiaoi.xiaoNumber }</td>
					<td ><i class="red_heart"></i>小艾名称：${xiaoi.xname }</td>
				</tr>				
				
				<tr>
					<td width="150"><i class="red_heart"></i>所属家庭组名字：${xiaoi.familygroup.groupName }</td>
					<td width="150"><i class="red_heart"></i>激活时间：${xiaoi.activationTime }</td>
				</tr>
				
				<c:if test="${not empty family.groupNumber}">
				<tr>
					<td width="150"><i class="red_heart"></i>家庭组地址：${family.city }${family.district }</td>
				</tr>
				</c:if>
	</table>
	
	<table class="table mt_20">
		<tr>
		    <th width="5%">序号</th>
		    <th width="5%">家庭组编号</th>
		    <th width="20%">小艾编号</th>
		    <th width="20%">使用时间</th>
			<th width="20%">使用详情</th>
			<!-- <th width="30%">家庭组地址</th> -->
		</tr>
		<c:forEach items="${xiaoilogList }" var="bigData" varStatus="state">
		<tr>
		    <td>${state.index+1}</td>
		    <td>${bigData.invokeGroupNumber}</td>
		    <td>${bigData.invokeXiaoNumber}</td>
			<td>${bigData.invokeTime }</td>
			<td>${bigData.invokeMethod }</td>
			<%-- <td>${bigData.invokeMethod }</td> --%>
		</tr>
		</c:forEach>
		<tr>
					<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${pageNow }">
						<input type="hidden" id="totalPage" value="${totalPage }">
						
						<input type="hidden" value="${refresh }" id="refresh">
							<a href="javascript:void(0)" onclick="firstPage(1,5)" id="firstPage" >首页</a>
							<a href="javascript:void(0)" onclick="prePage(${pageNow-1 },5)" id="upPage" >上一页</a>
							<a href="javascript:void(0)" onclick="nextPage(${pageNow+1 },5)" id="nextPage" >下一页</a>
							<a href="javascript:void(0)" onclick="endPage(${totalPage },5)" id="lastPage" >尾页</a>
							
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
						
					</td>
					</tr>
		
		
	</table>
	
	
	<!-- 地区图表 START -->
	<table id='myTable5'>
			<a href="javascript:void(0)" class="btn_submit mt_20"  target="_self">家庭组地址图表</a>
			<caption>用户地区分布</caption>
			<thead>
				<tr>
					<th></th>
					<c:if test="${not empty chartList }">
						<c:forEach items="${chartList }" var="charsingle">
							<th>${charsingle.address }</th>
						</c:forEach>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>${allcount }</th>
					<c:if test="${not empty chartList }">
						<c:forEach items="${chartList }" var="charsingle">
							<td>${charsingle.count }</td>
						</c:forEach>
					</c:if>
				</tr>
			</tbody>
		</table>  
	<!-- 地区图表 END -->
		
	<!-- 性别图表 START -->
		<table id='myTable1'>
		<a href="javascript:void(0)" class="btn_submit mt_20"  target="_self">用户性别图表</a>
			<caption>用户性别分布</caption>
			<thead>
				<tr>
					<th></th>
					<c:if test="${not empty userCharList }">
						<c:forEach items="${userCharList }" var="usercharsingle">
							<th>${usercharsingle.sex }</th>
						</c:forEach>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>${userAllCount }</th>
					<c:if test="${not empty userCharList }">
						<c:forEach items="${userCharList }" var="usercharsingle">
							<td>${usercharsingle.count }</td>
						</c:forEach>
					</c:if>
				</tr>
			</tbody>
		</table>  
	<!-- 性别图表 END -->
<a href="${pageContext.request.contextPath }/exportExcell.action?xiaoNumber=${xiaoi.xiaoNumber}" class="btn_submit mt_20">导出报表</a>
</div>

<!-- 图表 -->
<script type="text/javascript">
		//初始化图表插件
				gvChartInit();
				$(document).ready(function(){
					$('#myTable5').gvChart({
						chartType: 'PieChart',
						gvSettings: {
							vAxis: {title: 'No of players'},
							hAxis: {title: 'Month'},
							width: 1200,
							height: 600
						}
					});
				});
	 
		//初始化图标插件 600 350
			$(document).ready(function(){
					$('#myTable1').gvChart({
						chartType: 'PieChart',
						gvSettings: {
						vAxis: {title: 'No of players'},
						hAxis: {title: 'Month'},
						width: 1200,
						height: 600
					}
				});
			});
</script>
		
	
<script type="text/javascript">


$(document).ready(function(){
    var refresh=$("#refresh").val();
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="${pageContext.request.contextPath }/showXiaolog.action?pageNow=1&showPage=5";
	}
    $("#search_bt").click(function(){
      var xiaoinumber=$("#xiaoinu").val();
      if(xiaonumber==""){
        alert("小艾编号不能为空!");
        return false;
      }
    
    }); 
     
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/showXiaolog.action?pageNow="+(pageNow+1)+"&showPage=5";
		}
			
		
	});
	//上一页
	$("#upPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 if(pageNow-1 ==0){
			alert("已经是第一页了");
		 }else{
			window.location.href="${pageContext.request.contextPath }/showXiaolog.action?pageNow="+(pageNow-1)+"&showPage=5";
		 }
	});
	
	//首页
	$("#firstPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 if(pageNow==1){
		 	alert("已经是第一页了");
		 }else{
		 	window.location.href="${pageContext.request.contextPath }/showXiaolog.action?pageNow=1&showPage=5";
		 }
	
	});
	
	//末页	
	$("#lastPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 if(pageNow==totalPage){
		 	alert("已经是最后一页了");
		 }else{
		 	window.location.href="${pageContext.request.contextPath }/showXiaolog.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});

});

</script>
</body>
</html>
