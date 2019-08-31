<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cmfz" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script charset="utf-8" src="${cmfz}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${cmfz}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function (K) {
            K.create("#editor_id", {
                //指定上传文件的服务器端程序
                uploadJson: "${cmfz}/kindeditor/upload",
                //指定上传文件的form名
                filePostName: "img",
                //显示浏览远程服务器按钮---图片空间按钮
                allowFileManager: true,
                //指定浏览远程图片的服务器端程序---图片空间的路径
                fileManagerJson: "${cmfz}/kindeditor/allImages"
            })
        })
    </script>
</head>
<body>
<textarea id="editor_id" name="content" style="width:700px;height:300px;">
        &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
</body>
</html>
