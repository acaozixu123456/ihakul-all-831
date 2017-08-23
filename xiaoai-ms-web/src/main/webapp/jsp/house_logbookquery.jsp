<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾后台管理系统-家电信息</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
	<!-- 引入 dark 主题 -->
	<script src="${pageContext.request.contextPath }/js/dark.js"></script>
	<script src="${pageContext.request.contextPath }/js/loading.js"></script>  
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/animate.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/global.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/loading.css">
</head>
<body>
	<div class="main_title">
		<div class="fr blue"><a href="${pageContext.request.contextPath }/jsp/homeuser_query.jsp">返回&gt;&gt;</a></div>
		<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>电器使用情况</strong></a></div>
	</div>

<div class="container">
	

	<table class="table mt_20">
		<tr>
		    <th>序号</th>
			<th>家电名称</th>
			<th>品牌</th>
			<th>型号</th>
			<th>家电类别</th>
			<th>家庭组名称</th>
			<th>房间名称</th>
		</tr>
		<c:forEach items="${houseList }" var="hhlist" varStatus="star"> 
		<tr>
		  <td>${star.index+1 }</td>
			<td>${hhlist.eaName }</td>
			<td>${hhlist.brandName }</td>
			<td>${hhlist.brand }</td>
			<td>
			<c:if test="${hhlist.classId==1 }">
					红外
			</c:if>
			<c:if test="${hhlist.classId==2 }">
					智能
		    </c:if>
		    </td>
			<td>${hhlist.familygroup.groupName}</td>
			<td>${hhlist.room.roomName}</td>
		</tr>
	</c:forEach>
	<tr>
				<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${ pageNow}">
						<input type="hidden" id="totalPage"  value="${totalPage} ">
							<button type="button" id="firstPage" >首页</button>
							<button type="button" id="upPage" >上一页</button>
							<button type="button" id="nextPage" >下一页</button>
							<button type="button" id="lastPage" >尾页</button>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
					</td>
					</tr>
	
	</table>

	<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
	<div class="btn_submit mt_20" onclick="initChart()">生成近期家电数量图表</div>
	<div class="btn_submit mt_20" onclick="initChart2()">生成近期家电品牌图表</div>
    <div id="echart1" style="width: 1000px;height:500px;"></div>
    <div id="echart2" style="width: 1000px;height:500px;"></div>
</div>

<!-- echart图表 -->
 <script type="text/javascript">
 /**
  *等待框
  */
 function loading() {
 	$('body').loading({
 		loadingWidth:240,
 		title:'请稍等!',
 		name:'load_houseHold',
 		discription:'正在生成图表...',
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
 	})
 };
 function initChart(){
	 //加载等待框
	 /*loading();*/
     // 基于准备好的dom，初始化echarts实例
     var myChart = echarts.init(document.getElementById('echart1'),'dark');

     myChart.showLoading();

	 var arr = new Array();
  	 var arr2 = new Array();
	 $.ajax({
         //提交数据的类型 POST GET
         type:"POST",
         //提交的网址
         url:"${pageContext.request.contextPath }/echartHouseHold.action",
         //同步
         async: false,
         //提交的数据
         data:{"code":1},
         //返回数据的格式
         datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
         //在请求之前调用的函数
         //beforeSend:function(){$("#msg").html("logining");},
         //成功返回之后调用的函数             
         success:function(data){
         	var obj = JSON.parse(data);
         	//var arr = new Array();
         	//var arr2 = new Array();
         	for (var i=0;i<obj.length;i++){
	         	arr[i] = obj[i].householdName;
	         	arr2[i] = obj[i].count;
			}
         },
         //调用出错执行的函数
         error: function(){
             //请求出错处理
             alert("未知错误！");
         }         
      });
	 

     // 指定图表的配置项和数据
     var option = {
         title: {
             text: '近期家电数量图表'
         },
         toolbox: {
             show: true,
             orient: 'vertical',
             left: 'right',
             top: 'center',
             feature: {
                 dataView: {readOnly: false},
                 restore: {},
                 saveAsImage: {}
             }
         },
         visualMap: {
             min: 0,
             max: 100,
             left: 'left',
             top: 'bottom',
             text: ['高','低'],           // 文本，默认为数值文本
             calculable: true
         },
         tooltip: {},
         legend: {
             data:['个数']
         },
         xAxis: {
             //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        	 data:arr
         },
         yAxis: {},
         series: [{
             name: '个数',
             type: 'bar',
             //data: [5, 20, 36, 10, 10, 20]
         	 data:arr2
         }]
     };

     // 使用刚指定的配置项和数据显示图表。
     myChart.hideLoading();
     myChart.setOption(option);
     $('html, body, .content').animate({scrollTop: $(document).height()}, 1000);
     //取消等待框
     /*removeLoading('load_houseHold');*/
 }
 
 
 //【----近期家电品牌图表-----】
 function initChart2(){
	 //加载等待框
	 //loading();
     $('html, body, .content').animate({scrollTop: $(document).height()}, 1000);
     // 基于准备好的dom，初始化echarts实例
     var myChart = echarts.init(document.getElementById('echart2'),'dark');

	 var arr = new Array();
  	 var arr2 = new Array();
	 $.ajax({
         //提交数据的类型 POST GET
         type:"POST",
         //提交的网址
         url:"${pageContext.request.contextPath }/echart2HouseHold.action",
         //同步
         async: false,
         //提交的数据
         data:{"code":1},
         //返回数据的格式
         datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
         //在请求之前调用的函数
         //beforeSend:function(){$("#msg").html("logining");},
         //成功返回之后调用的函数             
         success:function(data){
         	var obj = JSON.parse(data);
         	//var arr = new Array();
         	//var arr2 = new Array();
         	for (var i=0;i<obj.length;i++){
	         	arr[i] = obj[i].householdName;
	         	arr2[i] = obj[i].count;
			}
         },
         //调用出错执行的函数
         error: function(){
             //请求出错处理
             alert("未知错误！");
         }         
      });

     // 指定图表的配置项和数据
     var option = {
         title: {
             text: '近期家电品牌图表'
         },
         tooltip: {},
         visualMap: {
             min: 0,
             max: 100,
             left: 'left',
             top: 'bottom',
             text: ['高','低'],           // 文本，默认为数值文本
             calculable: true
         },
         toolbox: {
             show: true,
             orient: 'vertical',
             left: 'right',
             top: 'center',
             feature: {
                 dataView: {readOnly: false},
                 restore: {},
                 saveAsImage: {}
             }
         },
         legend: {
             data:['品牌']
         },
         xAxis: {
             //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        	 data:arr
         },
         yAxis: {},
         series: [{
             name: '品牌',
             type: 'bar',
             //data: [5, 20, 36, 10, 10, 20]
         	 data:arr2
         }]
     };
     // 使用刚指定的配置项和数据显示图表。
     //取消等待框
     removeLoading('load_houseHold');
     myChart.setOption(option);
     $('html, body, .content').animate({scrollTop: $(document).height()}, 1000);

 }
</script>

<script type="text/javascript">
$(document).ready(function(){
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/findAllhhold.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="${pageContext.request.contextPath }/findAllhhold.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/findAllhhold.action?pageNow=1&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/findAllhhold.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});

});

</script>
</body>
</html>