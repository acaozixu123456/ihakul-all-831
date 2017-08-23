<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>小艾后台管理系统-MAC地址管理</title>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/date/js/laydate.js"></script>
	

	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
	<%-- <script src="${pageContext.request.contextPath }/js/loading.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/animate.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/global.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/loading.css"> --%>
	
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>MAC地址管理</strong></a></div>

<div class="container">
	<ul class="tabs_3 tabs clearfix" id="tabs">
		<!-- <li><a href="">家庭组列表</a></li> -->
		<!-- <li><a href="">添加家庭组</a></li> -->
	</ul>
	<ul class="tab_conbox" id="tab_conbox">
		<li class="tab_con">
		<form action="${pageContext.request.contextPath }/initXiaoiMac.action" method="post">
			<ul class="ad_list_search clearfix">
				
				<li class="name"><label>厂商名称</label>
				<select name="manufacturer">
					<c:if test="${not empty manufacturers }">
						<c:forEach items="${manufacturers }" var="manu">
							<option value="${manu.name }">${manu.id}.${manu.name }</option>
						</c:forEach>
					</c:if>
				</select>
				<!-- <input type="text" name="manufacturer" value="哈酷小i"> -->
				</li>
				<li class="name">
					<label>BYTE1</label>
					<br>
					<table>
						<tr>
							<td><input type="checkbox" name="BYTE1" value="1">PLC</td>
						</tr>
						<tr>
							<td><input type="checkbox" name="BYTE1" value="2">微功率无线</td>
						</tr>
						<tr>
							<td><input type="checkbox" name="BYTE1" value="4">Zigbee</td>
						</tr>
					</table>
				
				</li>
				
				<li class="name">
					<label>BYTE7</label>
					<br>
					<table>
						<tr>
							<td><input type="radio" name="BYTE7" value="1">标准版智能网关</td>
						</tr>
						<tr>
							<td><input type="radio" name="BYTE7" value="2">时尚版智能网关</td>
						</tr>
					</table>
				</li>
				
				<li ><button class="btn_submit mt_20" type="submit">生成二维码</button></li>
				
			</ul>
			
			
			<c:if test="${not empty filePath }">
				<ul class="ad_list_search clearfix">
					
					<li class="name">
					<img id="haku_mac" alt="哈酷小i维码" src="${filePath }">
					<%-- <img id="haku_mac" alt="哈酷小i维码" src="${pageContext.request.contextPath }/images/404_to_index.png"> --%>
					<%-- <a href="${filePath }">下载二维码</a> --%>
					<br>
					<a href="javascript:void(0)" onclick="printQR()" class="btn_submit mt_20"  target="_self">打印二维码</a>
					</li>
					
					<li class="name">
					<label>device.i</label>
						<br>
					<%--<input id="macAddress" type="text" name="BYTE7" value="${macAddress }" >--%>
						<a href="${macAddress}" class="btn_submit mt_20"  target="_self">下载device.i</a>
					</li>
					
				</ul>
			</c:if>
			</form>
	
			<table class="table mt_20">
				<tr>
					<td width="5%">序号</td>
					<td width="15%">MAC地址</td>
					<td width="20%">生成时间</td>
					<td width="5%">状态</td>
					<td width="20%">厂商编号</td>
					<td width="35%">MAC二维码下载链接</td>
				</tr>
				
				<c:if test="${ not empty macList }">
					<c:forEach var="macsingle" items="${macList }" varStatus="mac_index">
					<tr>
						<td>${mac_index.count }</td>
						<td>${macsingle.mac }</td>
						<td><fmt:formatDate value="${macsingle.creattime }" pattern="yyyy-MM-dd HH:mm"/></td>
						<c:if test="${macsingle.state eq 0}">
							<td>未激活</td>
						</c:if>
						<c:if test="${macsingle.state eq 1}">
							<td>已激活</td>
						</c:if>
						<td>${macsingle.manufacturer }</td>
						<c:if test="${ empty macsingle.uploadurl }">
							<td style="color: red">下载地址缺失</td>
						</c:if>
						<c:if test="${ not empty macsingle.uploadurl }">
							<td style="color: blue;"><a href="${macsingle.uploadurl }">点击下载</a></td>
						</c:if>
					</tr>
					</c:forEach>
					
					<tr>
					<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${pageNow }">
						<input type="hidden" id="totalPage" value="${totalPage }">
						
						<input type="hidden" value="${refresh }" id="refresh">
							<a href="javascript:void(0)"  id="firstPage" >首页</a>
							<a href="javascript:void(0)"  id="upPage" >上一页</a>
							<a href="javascript:void(0)"  id="nextPage" >下一页</a>
							<a href="javascript:void(0)"  id="lastPage" >尾页</a>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
						
					</td>
					</tr>
				</c:if>
			</table>

	
</ul>
</div>



<!-- 滑动标签js -->
<!-- 打印js 【C-Lodop】-->

<script src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
/**
 * loading框
 */
/* function loading7() {
	$('body').loading({
		loadingWidth:240,
		title:'请稍等!',
		name:'test',
		discription:'这是一个描述...',
		direction:'row',
		type:'origin',
		originBg:'#71EA71',
		originDivWidth:30,
		originDivHeight:30,
		originWidth:4,
		originHeight:4,
		smallLoading:false,
		titleColor:'#388E7A',
		loadingBg:'#312923',
		loadingMaskBg:'rgba(22,22,22,0.2)'
	});

	setTimeout(function(){
		removeLoading('test');
	},3000);
} */


/* 打印二维码 */
function printQR(){
	$("#haku_mac").jqprint();
}
 
/**
 * 下载二维码
 */
function downloadQR(filePath){
	/* window.location.href=filePath; */
	window.open(filePath);
}


$(document).ready(function() {
	var refresh=$("#refresh").val();
	if(refresh !=null){
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="${pageContext.request.contextPath }/preInitMac.action?pageNow=1&showPage=5";
	}
   }
	
	jQuery.jqtab = function(tabtit,tab_conbox,shijian) {
		$(tab_conbox).find("#tab_conbox > li").hide();
		$(tabtit).find("li:first").addClass("thistab").show(); 
		$(tab_conbox).find("li:first").show();
	
		$(tabtit).find("li").bind(shijian,function(){
		  $(this).addClass("thistab").siblings("li").removeClass("thistab"); 
			var activeindex = $(tabtit).find("li").index(this);
			$(tab_conbox).children().eq(activeindex).show().siblings().hide();
			return false;
		});
	
	};
	/*调用方法如下：*/
	$.jqtab("#tabs","#tab_conbox","click");	
	
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/preInitMac.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="${pageContext.request.contextPath }/preInitMac.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/preInitMac.action?pageNow=1&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/preInitMac.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});
	
});


</script>
</body>
</html>