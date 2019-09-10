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

    <title>APP开发者平台</title>

    <link href="${pageContext.request.contextPath }/statics/layui/css/layui.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/statics/layui/layui.js"></script>


    <style type="text/css">
        .body{background: #e2e2e2;height: 100%;width: 100%;}
        .div{margin: 10%;}
    </style>
</head>

<body class="body">


<div class="div" align="center">
    <form class="layui-form" action="dologin" method="post">
        <h1 style="font-weight: 900;">APP开发者平台</h1>
        <div style="margin-left: 40%;" class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="devCode" required   lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div style="margin-left: 40%;" class="layui-form-item">
            <div class="layui-input-inline">
                <input type="password" name="devPassword" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <span style="color:red;">${error }</span>
        <div style="margin-left:-180px;" class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">登录</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
        <hr class="layui-bg-black"/>
        <div>
            <p>©2016 All Rights Reserved. </p>
        </div>
    </form>
</div>


<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return true;
        });
    });
</script>

</body>
</html>