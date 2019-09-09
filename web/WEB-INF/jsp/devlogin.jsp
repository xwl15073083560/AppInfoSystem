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
    <script src="statics/layui/layui.js"></script>


    <style type="text/css">
        .body{background: #e2e2e2;height: 100%;width: 100%;}
        .div{margin: 10%;}
    </style>
</head>

<body class="body">
<div>
    <a class="hiddenanchor" id="signup"></a>
    <a class="hiddenanchor" id="signin"></a>

    <div class="login_wrapper">
        <div class="animate form login_form">
            <section class="login_content">
                <form action="dologin" method="post">
                    <h1>APP开发者平台</h1>
                    <div>
                        <input type="text" class="form-control" name="devCode" placeholder="请输入用户名" required="" />
                    </div>
                    <div>
                        <input type="password" class="form-control" name="devPassword" placeholder="请输入密码" required="" />
                    </div>
                    <span>${error }</span>
                    <div>
                        <button type="submit" class="btn btn-success">登     录</button>
                        <button type="reset" class="btn btn-default">重　填</button>
                    </div>

                    <div class="clearfix"></div>

                    <div class="separator">
                        <div>
                            <p>©2016 All Rights Reserved. </p>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </div>
</div>

<div class="div" align="center">
    <form class="layui-form" action="" method="post">
        <h1 style="font-weight: 900;">APP开发者平台</h1>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="text" name="title" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <span>${error }</span>
        <div class="layui-form-item">
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
</body>
</html>