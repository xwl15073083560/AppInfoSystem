<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<link href="${pageContext.request.contextPath }/statics/layui/css/layui.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath }/statics/layui/layui.js"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
	<div class="layui-header">
		<div class="layui-logo">
			<i style="font-size: 30px;color: white;" class="layui-icon">&#xe62e;</i>
			APP BMS
		</div>
		<!-- 头部区域（可配合layui已有的水平导航） -->
		<ul class="layui-nav layui-layout-right">
			<li class="layui-nav-item">
				<a href="javascript:;">
					<i  class="layui-icon">&#xe66f;</i> 管理员
				</a>
				<dl class="layui-nav-child">
					<dd><a href="">注销</a></dd>
				</dl>
			</li>
		</ul>
	</div>

	<div class="layui-side layui-bg-black">
		<div class="layui-side-scroll">
			<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
			<ul class="layui-nav layui-nav-tree"  lay-filter="test">
				<li class="layui-nav-item layui-nav-itemed">
					<a class="" href="javascript:;">APP账户管理</a>
					<dl class="layui-nav-child">
						<dd><a href="list1.html" target="frame1">APP开发者账户申请</a></dd>
						<dd><a href="list2.html" target="frame1">个人信息维护</a></dd>
					</dl>
				</li>
				<li class="layui-nav-item">
					<a href="javascript:;">APP应用管理</a>
					<dl class="layui-nav-child">
						<dd><a href="list1.html" target="frame1">APP维护</a></dd>
					</dl>
				</li>
			</ul>
		</div>
	</div>

	<div class="layui-body">
		<!-- 内容主体区域 -->
		<div style="padding: 15px;">
			<iframe name="frame1" id="frame1" width="1100" height="450" frameborder="0">
			</iframe>
		</div>
	</div>

	<div class="layui-footer">
		<!-- 底部固定区域 -->
		© layui.com - 底部固定区域
	</div>
</div>

<script>
	//JavaScript代码区域
	layui.use('element', function(){
		var element = layui.element;

	});


</script>
</body>
</html>
