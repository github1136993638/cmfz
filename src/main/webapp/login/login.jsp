<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="cmfz"></c:set>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="${cmfz}/login/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${cmfz}/login/assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${cmfz}/login/assets/css/form-elements.css">
    <link rel="stylesheet" href="${cmfz}/login/assets/css/style.css">
    <link rel="shortcut icon" href="${cmfz}/login/assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="${cmfz}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="${cmfz}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="${cmfz}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${cmfz}/login/assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${cmfz}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${cmfz}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${cmfz}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${cmfz}/login/assets/js/scripts.js"></script>
    <%--jquery表单验证插件--%>
    <script src="${cmfz}/boot/js/jquery.validate.min.js"></script>
    <style type="text/css">
        #psUsername {
            color: red;
            margin-top: 10px;
            margin-left: 5px;
        }

        #psPassword {
            color: red;
            margin-top: 5px;
            margin-left: 5px;
        }

        #psCode {
            color: red;
            margin-top: 5px;
            margin-left: 5px;
        }

        #psUsername.ok {
            color: green;
            margin-left: 5px
        }

        #psPassword.ok {
            color: green;
            margin-left: 5px
        }

        #psCode.ok {
            color: green;
            margin-left: 5px
        }

    </style>
    <script>
        function changeImage() {
            $("#imgVcode").attr({"src": "${cmfz}/image/imageCode?t=" + Math.random()});
        }

        //发ajax请求
        $(function () {
            $("#loginButtonId").click(function () {
                $.ajax({
                    url: "${cmfz}/admin/findAdmin",
                    dataType: "json",
                    data: $("#loginForm").serialize(),
                    type: "post",
                    success: function (data) {
                        //window.location.href="/main.jsp";
                        if (data.admin) {//或者可以用typeOf和===来判断存储的数据类型是Object还是String
                            window.location.href = "${cmfz}/back/main.jsp";
                        } else {
                            $("#loginError").html("<strong>" + data.loginError + "<strong>");
                        }
                    }
                })
            })

        })
        //功能函数
        //校验用户名和密码
        function checkUsername() {
            //通过dom提供的api获取  username文本框标签对象
            var username = $("#txtUsername").val();
            //alert(username);
            if (username.length == 0) {
                $("#psUsername").text("用户名不能为空！");
                //清除span标签的样式
                $("#psUsername").removeClass();
            } else {

                $("#psUsername").text("√");
                //设置span标签的样式---类选择器
                $("#psUsername").addClass("ok");

            }
        }

        function checkPassword() {
            var password = $("#txtPassword").val();
            if (password.length == 0) {
                $("#psPassword").text("密码不能为空！");
                //清除span标签的样式
                $("#psPassword").removeClass();
            } else {
                if (password.length < 5) {
                    $("#psPassword").text("密码不能小于5位！");
                    //清除span标签的样式
                    $("#psPassword").removeClass();
                } else {
                    $("#psPassword").text("√");
                    //设置span标签的样式---类选择器
                    $("#psPassword").addClass("ok");
                }

            }
        }

        function checkCode() {
            var code = $("#form-code").val();
            if (code.length == 0) {
                $("#psCode").text("验证码不能为空！");
                $("#psCode").removeClass();
            } else if (code.length != 4) {
                $("#psCode").text("验证码必须为四位！");
                $("#psCode").removeClass();
            } else {
                $("#psCode").text("√");
                $("#psCode").addClass("ok");
            }
        }

        function checkForm() {

            checkUsername();
            checkPassword();
            checkCode();

            var num = $(".ok");
            if (num.length == 3) {
                return true;
            } else {
                return false;
            }

        }

        $(function () {

            //为标签添加事件
            $("loginForm").submit(function () {
                return checkForm();
            });
            $("#txtUsername").blur(function () {
                checkUsername();
            });
            $("#txtPassword").blur(function () {
                checkPassword();
            });
            $("#form-code").blur(function () {
                checkCode();
            });

        });

    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" id="txtUsername" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div id="psUsername">*</div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" id="txtPassword" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div id="psPassword">*</div>
                            <div class="form-group">
                                <img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="${cmfz}/image/imageCode" onClick="changeImage()">
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="enCode" id="form-code">
                            </div>
                            <div id="psCode">*</div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                            <br><span id="loginError"></span>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>