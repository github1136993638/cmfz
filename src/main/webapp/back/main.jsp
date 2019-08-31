<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set value="${pageContext.request.contextPath}" var="cmfz"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>持明法洲</title>
    <%--脚标--%>
    <link rel="icon" href="${cmfz}/img/favicon.ico">
    <link rel="stylesheet" href="${cmfz}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${cmfz}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${cmfz}/jqgrid/css/jquery-ui.css">
    <!--jquery的js包-->
    <script src="${cmfz}/boot/js/jquery-2.2.1.min.js"></script>
    <!--bootstrap的js包-->
    <script src="${cmfz}/boot/js/bootstrap.min.js"></script>
    <%--核心的国际化js包--%>
    <script src="${cmfz}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqgrid的核心js文件--%>
    <script src="${cmfz}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${cmfz}/boot/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="${cmfz}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${cmfz}/kindeditor/lang/zh-CN.js"></script>
    <%--echarts--%>
    <script src="${cmfz}/echarts/echarts.min.js"></script>
    <script src="${cmfz}/echarts/china.js"></script>
    <script src=http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">

        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法洲管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <shiro:authenticated>
            <span style="float: right; margin-top: 15px">欢迎：<shiro:principal></shiro:principal>
                <a href="${cmfz}/admin/exit">
                退出登录<span class="glyphicon glyphicon-log-in"></span>
                </a>
            </span>
            </shiro:authenticated>
            <shiro:notAuthenticated>
                你好，请登陆<a href="${cmfz}/login/login.jsp">点击登陆</a>
            </shiro:notAuthenticated>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<div class="container-fluid">

    <div class="row">
        <!--这是左边-->
        <div class="col-sm-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <!--第一个面板-->
                <div class="panel panel-default">
                    <!--面板的头部-->
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <!--面板的body-->
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${cmfz}/back/userList.jsp')" id="btnUser"
                               style="text-decoration:none">用户列表</a>
                        </div>
                    </div>
                </div>
                <!--第二个面板-->
                <div class="panel panel-default">
                    <!--面板的头部-->
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                               aria-expanded="true" aria-controls="collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>

                    <!--面板的body-->
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${cmfz}/back/userList.jsp')" id="btnGuru"
                               style="text-decoration:none">上师列表</a>
                        </div>
                    </div>
                </div>
                <!--第三个面板-->
                <div class="panel panel-default">
                    <!--面板的头部-->
                    <%--<div class="panel-heading" role="tab" id="headingThree">--%>
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
                               aria-expanded="true" aria-controls="collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>

                    <!--面板的body-->
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${cmfz}/back/articleList.jsp')" id="btnArticle"
                               style="text-decoration:none">文章列表</a>
                        </div>
                    </div>
                </div>
                <!--第四个面板-->
                <div class="panel panel-default">
                    <!--面板的头部-->
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour"
                               aria-expanded="true" aria-controls="collapseFour">
                                专辑管理
                            </a>
                        </h4>
                    </div>

                    <!--面板的body-->
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingFour">
                        <div class="panel-body">
                            <a href="javascript:$('#content').load('${cmfz}/back/album.jsp')" id="btnAlbum"
                               style="text-decoration:none">
                                专辑列表
                            </a>
                        </div>
                    </div>
                </div>

                <!--第五个面板-->
                <div class="panel panel-default">
                    <!--面板的头部-->
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive"
                               aria-expanded="true" aria-controls="collapseFive">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <shiro:hasPermission name="banner:*">
                        <!--面板的body-->
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingFive">
                            <div class="panel-body">
                                <a href="javascript:$('#content').load('${cmfz}/back/bannerList.jsp')" id="btnPicture"
                                   style="text-decoration:none">轮播图列表</a>
                            </div>
                        </div>
                    </shiro:hasPermission>
                </div>
                <%--<shiro:hasAnyRoles name="super,SSSVIp">--%>
                <shiro:hasRole name="super">
                    <%--第六个面板--%>
                    <div class="panel panel-default">
                        <!--面板的头部-->
                        <div class="panel-heading" role="tab" id="headingSix">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseSix"
                                   aria-expanded="true" aria-controls="collapseSix">
                                    管理员管理
                                </a>
                            </h4>
                        </div>
                        <!--面板的body-->
                        <div id="collapseSix" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingSix">
                            <div class="panel-body">
                                <a href="javascript:$('#content').load('${cmfz}/back/bannerList.jsp')" id="btnadmin"
                                   style="text-decoration:none">管理员列表</a>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
            </div>
        </div>
        <!--这是右边-->
        <div id="content" class="col-sm-10">
            <!-- 巨幕-->
            <div class="jumbotron">
                <h4>欢迎来到持明法洲后台管理系统</h4>
            </div>
            <%--图片--%>
            <%--<div>
                <img src="${cmfz}/img/shouye.jpg">
            </div>--%>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <%--<li data-target="#carousel-example-generic" data-slide-to="2"></li>--%>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="${cmfz}/img/shouye.jpg">
                    </div>
                    <div class="item">
                        <img src="${cmfz}/img/2.jpg">
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            <!-- Tab panes -->
            <%--<div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">

                    <!--表单-->
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form class="form-inline" id="form_dd" action="http://localhost:8989/user/user">
                                <div class="form-group">
                                    <label for="exampleInputName2">客户名称</label>
                                    <input type="text" class="form-control" id="exampleInputName2" placeholder="Jane Doe">
                                </div>
                                <label for="exampleInputName2">客户名称
                                    <select class="form-control">
                                        <option>---请选择---</option>
                                    </select>
                                </label>
                                <label for="exampleInputName2">所属行业
                                    <select class="form-control">
                                        <option>---请选择---</option>
                                    </select>
                                </label>
                                <label for="exampleInputName2">客户级别
                                    <select class="form-control">
                                        <option>---请选择---</option>
                                    </select>
                                </label>
                                <button type="submit" class="btn btn-primary">查询</button>
                            </form>
                        </div>
                    </div>

                    <!--面板 面板里面存放 表格-->
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">用户列表</div>

                        <table class="table table-striped table-bordered table-hover table-condensed">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Bir</th>
                                <th>Option</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="active">
                                <td>1</td>
                                <td>张三</td>
                                <td>2012-12-12</td>
                                <td>
                                    <button class="btn btn-danger">删除</button> <button class="btn btn-primary" data-target="#myModal" data-toggle="modal">修改</button></td>
                            </tr>
                            <tr class="success">
                                <td>1</td>
                                <td>张三</td>
                                <td>2012-12-12</td>
                                <td><button class="btn btn-danger">删除</button> <button class="btn btn-primary" data-target="#myModal" data-toggle="modal">修改</button></td>
                            </tr>
                            <tr class="warning">
                                <td>1</td>
                                <td>张三</td>
                                <td>2012-12-12</td>
                                <td><button class="btn btn-danger">删除</button> <button class="btn btn-primary" data-target="#myModal" data-toggle="modal">修改</button></td>
                            </tr>
                            <tr class="danger">
                                <td>1</td>
                                <td>张三</td>
                                <td>2012-12-12</td>
                                <td><button class="btn btn-danger">删除</button> <button class="btn btn-primary" data-target="#myModal" data-toggle="modal">修改</button></td>
                            </tr>
                            <tr>
                                <td >1</td>
                                <td >张三</td>
                                <td >2012-12-12</td>
                                <td ><button class="btn btn-danger">删除</button> <button class="btn btn-primary" data-target="#myModal" data-toggle="modal">修改</button></td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </div>

            </div>--%>

        </div>

    </div>
    <br>
    <%--这是底部面板--%>
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body" style="text-align: center">
                @百知教育 baizhi@zparkhr.com.cn
            </div>
        </div>
    </div>

</div>
<%--模态框--%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>