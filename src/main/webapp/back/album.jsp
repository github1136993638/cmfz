<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cmfz" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url: "${cmfz}/album/findAllAlbum",
            datatype: "json",
            colNames: ["id", "标题", "分数", "作者", "播音员", "章节数", "专辑简介", "上传时间", "状态", "封面"],
            colModel: [
                {name: "id", align: "center"},
                {name: "title", editable: true, align: "center"},
                {name: "score", align: "center"},
                {name: "author", editable: true, align: "center"},
                {name: "broadcast", editable: true, align: "center"},
                {name: "count", align: "center"},
                {name: "brief", editable: true, align: "center"},
                {name: "publish_date", align: "center"},//{name:"createDate",formatter:"date"},
                {
                    name: "status", editable: true, edittype: "select", align: "center",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                    //渲染静态数据
                    // editoptions:{value:"1:研发部;2:教学部"},
                    // 加载远程数据渲染下拉列表,要求响应的是一段html元素内容
                },//该字段可编辑

                {
                    name: "cover", editable: true, edittype: "file", align: "center",
                    formatter: function (cellvalues, options, rowObject) {
                        //cellvalues-当前字段的值 options-表格中的所有属性 rowObject-当前行数据对象
                        return "<img style='height:60px;width:100px' src='${cmfz}/upload/img/" + cellvalues + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,//自适应宽度
            viewrecords: true,//显示总条数
            pager: "#albumPaper",//分页组件
            page: 1,////指定初始化页码  默认进来在第一页
            rowNum: "2",// 指定默认每页展示的条数，值必须来自rowList中的一个
            rowList: [2, 4, 6, 8, 10],
            height: "60%",
            multiselect: true,// 在表格第一列前面，加入复选框
            editurl: "${cmfz}/album/editAlbum",//增删改查方法
            subGrid: true,//开启子表格/二级表格支持
            //subGridId  子表格的Id,当开启子表格式,会在主表格中创建一个子表格，subgrid_id就是这个子表格的Id
            //albumId当前行（数据）的id
            subGridRowExpanded: function (subGridId, albumId) {
                addSubGrid(subGridId, albumId);
            }
        }).jqGrid("navGrid", "#albumPaper",
            //参数一：navGrid 参数二:#bannerPaper分页控件的id选择器
            {edit: true, add: true, del: false, search: false},
            {
                // 执行编辑时进行的操作
                closeOnEscape: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                beforeShowForm: function (frm) {//frm参数名随意---内置对象document
                    $("#bannerList").jqGrid("getGridParam", "selrow");
                    frm.find("#title").attr("readOnly", true);//将字段设置为只读
                    frm.find("#author").attr("readOnly", true);
                    frm.find("#brief").attr("readOnly", true);
                    frm.find("#broadcast").attr("readOnly", true);
                    frm.find("#cover").attr("disabled", true);
                    return frm;
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
                    var albumId = response.responseText;//获取controller返回的id
                    console.log(albumId);
                    //发ajax请求
                    $.ajaxFileUpload({
                        url: "${cmfz}/album/uploadCover",
                        fileElementId: "cover",
                        data: {"albumId": albumId},
                        success: function (data) {
                            $("#albumList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {
                // 执行删除时进行的操作
            }
        )
    })

    //构建子表
    function addSubGrid(subGridId, albumId) {//subGridId:子表格的id  albumId：当前子表所属专辑的id
        var tableId = subGridId + "table";//子表格的table id
        var pagerId = subGridId + "pager";//子表格的page id
        console.log("tableId======" + tableId);
        console.log("pagerId======" + pagerId);
        $("#" + subGridId).html(
            "<table id='" + tableId + "' ></table>" +
            "<div id='" + pagerId + "'></div>"
        );
        $("#" + tableId).jqGrid({
            url: "${cmfz}/chapter/findAllChapter?albumId=" + albumId,
            datatype: "json",
            colNames: ["id", "标题", "大小", "时长", "状态", "上传日期", "音频", "操作"],
            colModel: [
                {name: "id", align: "center"},
                {name: "title", editable: true, align: "center"},
                {name: "size", align: "center"},//大小
                {name: "duration", align: "center"},//时长
                {
                    name: "status", editable: true, edittype: "select", align: "center",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name: "create_date", align: "center"},
                //{name:"aid",align: "center"},
                {name: "audio", align: "center", editable: true, edittype: "file"},
                //音频上传到服务器并在线播放    原文链接：https://blog.csdn.net/zhaihao1996/article/details/99655758
                {
                    name: "audio", align: "center",
                    formatter: function (cellvalues, options, rowObject) {
                        //cellvalues-当前字段的值 options-表格中的所有属性?当前单元格操作属性对象 rowObject-当前行的数据对象
                        //函数的返回值会显示在当前单元格
                        //法一：
                        return "<a href='#' onclick='playerAudio(\"" + cellvalues + "\")'> <span class='glyphicon glyphicon-play'></span></a>" + "     " +
                            "<a href='#' onclick='downloadAudio(\"" + rowObject.id + "\")'> <span class='glyphicon glyphicon-circle-arrow-down'></span></a>";
                        //法二：
                        /*return "<audio controls loop>\n" +
                            "  <source src=\"/upload/audio/"+cellvalues+"\" type=\"audio/mpeg\">\n" +
                            "</audio>";*/
                    }
                },
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            pager: "#" + pagerId,
            height: "60%",
            page: 1,//指定初始化页码
            rowNum: 2,// 指定默认每页展示的条数，值必须来自rowList中的一个
            viewrecords: true,// 是否显示总记录条数
            rowList: [2, 4, 6],
            multiselect: true,// 在表格第一列前面，加入复选框  可以实现多选
            editurl: "${cmfz}/chapter/editChapter?albumId=" + albumId,//增删改查方法
            caption: "专辑详细信息", //二级表格表头
        }).jqGrid("navGrid", "#" + pagerId,
            //参数一：navGrid 参数二:#bannerPaper分页控件的id选择器
            {edit: true, add: true, del: false, search: false},
            {
                // 执行编辑时进行的操作
                closeOnEscape: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true,
                beforeShowForm: function (frm) {//frm参数名随意---内置对象document
                    $("#" + tableId).jqGrid("getGridParam", "selrow");
                    frm.find("#title").attr("readOnly", true);
                    frm.find("#audio").attr("disabled", true);
                    return frm;
                },
            },
            {
                // 执行添加时进行的额外操作
                //reloadAfterSubmit:true,
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var chapterId = response.responseText;//获取controller返回的id
                    //发ajax请求
                    $.ajaxFileUpload({
                        url: "${cmfz}/chapter/uploadAudio",
                        fileElementId: "audio",//文件上传空间的id属性  <input type="file" id="file" name="file" />
                        //type:"POST",
                        data: {"chapterId": chapterId},
                        success: function (data) {
                            $("#" + tableId).trigger("reloadGrid");//刷新子表格
                            $("#albumList").trigger("reloadGrid");//操作完成后，刷新父表格
                        }
                    });
                    return response;
                }
            },
            {
                // 执行删除时进行的操作
                afterComplete: function (response) {
                    $("#" + tableId).trigger("reloadGrid");//刷新子表格
                    $("#albumList").trigger("reloadGrid");//操作完成后，刷新父表格
                }
            }
        )
    }

    function downloadAudio(id) {
        location.href = "${cmfz}/chapter/download?id=" + id;
    }

    function playerAudio(fileName) {
        /*展示音频标签*/
        $("#playAudioDiv").modal("show");
        /*传入音频路径*/
        $("#playAudioId").attr("src", "${cmfz}/upload/audio/" + fileName);
    }

</script>
<table id="albumList"></table>
<div id="albumPaper"></div>
<%--在线播放音频--%>
<div id="playAudioDiv" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="playAudioId" src="" controls></audio>
    </div>
</div>