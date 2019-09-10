<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cmfz" value="${pageContext.request.contextPath}"></c:set>

<script>
    $(function () {
        $("#articleList").jqGrid({
            url: "${cmfz}/article/findAllArticle",
            datatype: "json",
            colNames: ["id", "标题", "作者", "内容", "发布时间", "状态", "操作"],
            colModel: [
                {name: "id", align: "center"},
                {name: "title", editable: true, align: "center"},
                {name: "author", editable: true, align: "center"},
                {name: "content", hidden: true, align: "center"},
                {name: "publish_date", align: "center"},//{name:"createDate",formatter:"date"},
                {
                    name: "status", editable: true, edittype: "select", align: "center",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                    //渲染静态数据
                    // editoptions:{value:"1:研发部;2:教学部"},
                    // 加载远程数据渲染下拉列表,要求响应的是一段html元素内容
                },//该字段可编辑
                {
                    name: "", align: "center",
                    formatter: function (cellvalues, options, rowObject) {
                        //cellvalues-当前字段的值 options-表格中的所有属性?当前单元格操作属性对象 rowObject-当前行的数据对象
                        //函数的返回值会显示在当前单元格
                        //查看详细信息，并可以修改信息
                        return "<a href='#'onclick=\"lookOneModel('" + rowObject.id + "')\"> <span class='glyphicon glyphicon-th-list'></span></a>" + "     " +
                            "<a href='#'onclick=\"lookModel('" + rowObject.id + "')\"> <span class='glyphicon glyphicon-pencil'></span></a>";
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,//自适应宽度
            viewrecords: true,//显示总条数
            pager: "#articlePaper",//分页组件
            page: 1,////指定初始化页码  默认进来在第一页
            rowNum: "2",// 指定默认每页展示的条数，值必须来自rowList中的一个
            rowList: [2, 4, 6, 8, 10],
            height: "60%",
            multiselect: true,// 在表格第一列前面，加入复选框
            //editurl: "/article/editArticle",//增删改查方法
        })
    });

    //添加---模态框
    function showModel() {
        $("#myModal").modal("show");
        $("#addArticleFrom")[0].reset();//模态框内容体的表单  [0]-表示id
        $("#articleId").val("");//清空id
        $("#title").attr("readOnly", false);
        $("#author").attr("readOnly", false);
        $("#status").attr("readOnly", false);
        $("#modal_footer").html(
            "<button type=\"button\" onclick=\"addArticle()\" class=\"btn btn-primary\">添加</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        );
        KindEditor.create("#editor", {
            //指定上传文件的服务器端程序
            uploadJson: "${cmfz}/kindeditor/upload",
            //指定上传文件的form名
            filePostName: "img",
            //显示浏览远程服务器按钮---图片空间按钮
            allowFileManager: true,
            //指定浏览远程图片的服务器端程序---图片空间的路径
            fileManagerJson: "${cmfz}/kindeditor/allImages",
            cssData: ".ke-content img {max-width: 400px;height:350px;}",
            afterBlur: function () {
                this.sync();//当编辑框失去焦点时，将文本框中的内容同步到content，传递到后台
            }
        });
        KindEditor.html("#editor", "");//清空编辑框
    }

    function addArticle() {
        $.ajax({
            url: "${cmfz}/article/addArticle",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),//表单序列化数据
            success: function () {
                $("#articleList").trigger("reloadGrid");
                $("#myModal").modal("toggle");//添加成功后，关闭模态框
            }
        })
    }

    //查看文章
    function lookOneModel(id) {
        $("#myModal").modal("show");//展示模态框
        var value = $("#articleList").jqGrid("getRowData", id);//根据id获取查看行的数据
        $("#title").val(value.title).attr("readOnly", true);
        $("#author").val(value.author).attr("readOnly", true);
        $("#status").val(value.status).attr("readOnly", true);
        $("#articleId").val(value.id);
        $("#publish_date").val(value.publish_date);
        $("#modal_footer").html(
            ""
        );
        KindEditor.create('#editor', {
            uploadJson: "${cmfz}/kindeditor/upload",
            filePostName: "img",
            allowFileManager: true,
            fileManagerJson: "${cmfz}/kindeditor/allImages",
            afterBlur: function () {
                this.sync();
            }
        });
        KindEditor.html("#editor", "");//清空上一次的编辑框内容
        KindEditor.appendHtml("#editor", value.content);//回显本次编辑框的content字段内容
    }

    //修改---模态框
    function lookModel(id) {
        $("#myModal").modal("show");//展示模态框
        var value = $("#articleList").jqGrid("getRowData", id);//根据id获取查看行的数据
        console.log(value);
        $("#title").val(value.title).attr("readOnly", true);
        $("#author").val(value.author).attr("readOnly", true);
        $("#status").val(value.status);
        $("#articleId").val(value.id);
        $("#publish_date").val(value.publish_date);
        $("#modal_footer").html(
            "<button type=\"button\" onclick=\"updateArticle()\" class=\"btn btn-primary\">修改</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        );
        KindEditor.create('#editor', {
            uploadJson: "${cmfz}/kindeditor/upload",
            filePostName: "img",
            allowFileManager: true,
            fileManagerJson: "${cmfz}/kindeditor/allImages",
            cssData: ".ke-content img {max-width: 400px;height:350px;}",
            afterBlur: function () {
                this.sync();
            }
        });
        KindEditor.html("#editor", "");//清空编辑框
        KindEditor.appendHtml("#editor", value.content);//回显编辑框的content字段内容
    }

    function updateArticle() {
        $.ajax({
            url: "${cmfz}/article/updateArticle",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function () {
                $("#articleList").trigger("reloadGrid");
                $("#myModal").modal("toggle");
            }
        })
        /*$.post(
            "/article/updateArticle",
            $("#addArticleFrom").serialize(),
            function () {
                $("#articleList").trigger("reloadGrid");
                $("#myModal").modal("toggle");
            },
            "json"
        )*/
    }

    $(function () {
        $("#search").click(function () {
            var val = $("#esValue").val();
            $('#content').load('${cmfz}/back/esArticle.jsp?val=' + val);
        })
    })

</script>
<div class="row">
    <div class="col-lg-4">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active" id="active"><a href="#home" aria-controls="home" role="tab"
                                                              data-toggle="tab">文章列表</a></li>
            <li role="presentation"><a href="#profile" onclick="showModel()" aria-controls="profile" role="tab"
                                       data-toggle="tab">添加文章</a></li>
        </ul>
    </div>
    <div class="col-lg-4">
        <div class="input-group">
            <input type="text" id="esValue" class="form-control" placeholder="请输入关键字">
            <span class="input-group-btn">
            <button id="search" class="btn btn-default" type="button">搜索</button>
            </span>
        </div><!-- /input-group -->
    </div>
</div>
<table id="articleList"></table>
<div id="articlePaper" style="height: 50px"></div>
<%--模态框--%>
<div class="modal fade" id="myModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>

            <!--模态框内容体-->
            <div class="modal-body">
                <form action="${cmfz}/article/editArticle" class="form-horizontal"
                      id="addArticleFrom">
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="hidden" name="id" id="articleId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">作者</label>
                        <div class="col-sm-5">
                            <input type="text" name="author" id="author" placeholder="请输入作者" class="form-control">
                        </div>
                    </div>
                    <input type="hidden" name="publish_date" id="publish_date" class="form-control">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="展示">展示</option>
                                <option value="不展示">不展示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">
                <%--<button type="button" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>--%>
            </div>
        </div>
    </div>
</div>
