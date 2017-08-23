<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>用户登录小艾后台管理系统</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>

<div class="login_container">
	<div class="login_main">
		<div class="login_box">
			<div class="login_logo"><h1>小艾后台管理系统</h1></div>
			<div class="login_info_bd">
			<form  action="${pageContext.request.contextPath }/loginAction.action" method="post">
				<ul class="info">
					<li>
						<label>账号：</label><input type="text" class="input" id="a" name="aname">
					</li>
					<li>
						<label>密码：</label><input type="password" class="input" id="pass" name="password">
					</li>
					<li>
						<button class="login_btn" type="button" onclick="loginAdmin()">登录</button>
						<!-- <button class="login_btn" type="button" onclick="addAdmin()">注册</button> -->
					</li>
				</ul>
			</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	
	/* 注册按钮点击 */
	function addAdmin(){
		window.location.href="${pageContext.request.contextPath }/jsp/add_admin.jsp"; 
	}

	/* 登录按钮点击 */
	function loginAdmin(){
	//var data = JSON.stringify();
		  $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:"${pageContext.request.contextPath }/loginAction.action",
            //提交的数据
            data:{"aname":$("#a").val(),"password":$("#pass").val()},
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            //beforeSend:function(){$("#msg").html("logining");},
            //成功返回之后调用的函数             
            success:function(data){
            	var obj = JSON.parse(data);
           		if(obj.code==0){
           			/* 登录成功！跳转 */
           			alert("登录成功！");
           			window.location.href="${pageContext.request.contextPath }/jsp/main.jsp";
           		}else if(obj.code==1){
           			alert(obj.message);
           			/* 清空输入框 */
           			$("#pass").val("");
           			$("#a").val("").focus();
           		}            
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
                alert("未知错误！");
            }         
         });
	}
</script>
</body>
</html>