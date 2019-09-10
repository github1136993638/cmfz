<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(function () {
        var val = '${param.val}';
        $.ajax({
            url: "${pageContext.request.contextPath}/article/findArticle",
            datatype: "json",
            type: "post",
            data: {val: val},
            success: function (data) {
                $.each(data, function (index, value) {
                    $("#esMessage").append(
                        '<div class="media">\n' +

                        '  <div class="media-body">\n' +
                        '    <h4 class="media-heading">' + value.title + '</h4>\n' +
                        '作者： ' + value.author + '<br/>' +
                        '内容： ' + value.content + '<br/>' +

                        '    ...\n' +
                        '  </div>\n' +
                        '</div>')
                })
            }
        })
    })
</script>
<div id="esMessage">

</div>