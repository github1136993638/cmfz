<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cmfz" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        $("#bannerList").jqGrid({
            url: "${cmfz}/banner/findAllBanner",
            datatype: "json",
            colNames: ["id", "title", "description", "status", "create_date", "img_path"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "description", editable: true},
                {
                    name: "status", editable: true, edittype: "select",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },//该字段可编辑
                {name: "create_date"},//{name:"createDate",formatter:"date"},
                {
                    name: "img_path", editable: true, edittype: "file",
                    formatter: function (cellvalues, options, rowObject) {
                        //cellvalues-当前字段的值 options-表格中的所有属性 rowObject-当前行数据对象
                        return "<img style='height:60px;width:100px' src='${cmfz}/upload/img/" + cellvalues + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,//自适应宽度
            viewrecords: true,//显示总条数
            pager: "#bannerPaper",
            page: 1,//默认进来在第一页
            rowNum: "2",
            rowList: [2, 4, 6, 8, 10],
            height: "60%",
            multiselect: true,
            editurl: "${cmfz}/banner/editBanner",//增删改查方法
        }).jqGrid("navGrid", "#bannerPaper",
            //参数一：navGrid 参数二:#bannerPaper分页控件的id选择器
            {edit: true, add: true, del: true, search: false},
            {
                // 执行编辑时进行的操作
                closeOnEscape: true,
                reloadAfterSubmit: true,
                closeAfterAdd: true,
                beforeShowForm: function (frm) {//frm参数名随意---内置对象document
                    $("#bannerList").jqGrid("getGridParam", "selrow");
                    frm.find("#title").attr("readOnly", true);//将字段设置为只读
                    frm.find("#description").attr("readOnly", true);
                    frm.find("#img_path").attr("disabled", true);
                    return "aaa";
                },
            },
            {
                // 执行添加时进行的额外操作
                //reloadAfterSubmit:true,
                closeAfterAdd: true,
                // beforeShowForm:function(frm){
                //     $("#bannerList").jqGrid("getGridParam","selrow");
                //     frm.find("#img_path").attr("readOnly",false);
                // },
                afterSubmit: function (response) {
                    var bannerId = response.responseText;//获取controller返回的id
                    console.log(bannerId);
                    //发ajax请求
                    $.ajaxFileUpload({
                        url: "${cmfz}/banner/upload",
                        fileElementId: "img_path",
                        data: {"bannerId": bannerId},
                        success: function (data) {
                            $("#bannerList").trigger("reloadGrid");
                        }
                    })
                    return "aaa";
                }
            },
            {
                // 执行删除时进行的操作
            }
        )
    })
</script>

<table id="bannerList"></table>
<div id="bannerPaper"></div>