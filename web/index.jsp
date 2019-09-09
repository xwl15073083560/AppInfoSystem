<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理系统</title>
    <style type="text/css">
    .body{background: #e2e2e2;height: 100%;width: 100%;}
    .div{margin: 10%;}
</style>
    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath }/statics/css/custom.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath }/statics/layui/css/layui.css" rel="stylesheet">
    <script src="statics/layui/layui.js"></script>
</head>

<body class="body">
<div class="div" align="center">
    <h1 style="font-weight: 900">APP信息管理平台</h1>
    <br/>
    <div class="layui-input-inline">
        <a href="manager/login" class="layui-btn layui-btn-radius layui-btn-warm layui-input-inline">后台管理系统 入口</a>
    </div>
    <div class="layui-input-inline">
        <a href="dev/login" class="layui-btn layui-btn-radius layui-btn-warm">开发者平台 入口</a>
    </div>
</div>
</body>
</html>