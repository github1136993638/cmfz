<%@page pageEncoding="UTF-8" contentType="text/html;utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cmfz" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        $("#userList").jqGrid({
            styleUI: 'Bootstrap',
            url: "${cmfz}/user/findAllUser", //用来指定服务端的url地址 或  用来获取远程数据的url
            datatype: "json",   //用来指定从服务器端返回的数据类型 (默认是:"xml") 使用时:"json"
            colNames: ["ID", "头像", "姓名", "法名", "性别", "手机号码", "密码", "省份", "城市", "签名", "状态", "创建时间", "上师"],
            pager: "#userPager",
            page: 1,//默认进来在第一页
            rowNum: "2",
            rowList: [2, 4, 6, 8, 10],
            viewrecords: true,//显示总条数
            autowidth: true,//自适应宽度
            editurl: "${cmfz}/user/updateUser",//修改方法
            colModel: [
                {name: "id", align: "center"},
                {
                    name: "head_picture", align: "center", edittype: "file",
                    formatter: function (cellvalues, options, rowObject) {
                        //cellvalues-当前字段的值 options-表格中的所有属性 rowObject-当前行数据对象
                        return "<img style='height:60px;width:100px' src='${cmfz}/upload/img/" + cellvalues + "'/>"
                    }
                },
                {name: "name", align: "center"},
                {name: "dharma", align: "center"},
                {name: "sex", align: "center"},
                {name: "phone", align: "center"},
                {name: "password", align: "center"},
                {name: "province", align: "center"},
                {name: "city", align: "center"},
                {name: "sign", align: "center"},
                {
                    name: "status", editable: true, edittype: "select", align: "center",
                    editoptions: {value: "展示:展示;不展示:不展示"}
                },
                {name: "create_date", align: "center"},
                {name: "guru", align: "center"}
            ],
            height: "60%",
            multiselect: true,

        }).jqGrid("navGrid", "#userPager",
            {edit: true, add: false, del: false, search: false},//增删改查控件的展示
            {
                // 执行编辑时进行的操作
                closeOnEscape: true,
                reloadAfterSubmit: true,
                closeAfterEdit: true
            },
            {
                //执行添加时进行的操作
            },
            {
                //执行删除时进行的操作
            }
        )
        //用户地区分布图
        // 基于准备好的dom，初始化echarts实例
        var myEcharts = echarts.init(document.getElementById("localDom"));
        //指定图表的配置项和数据
        var option = {
            title: {
                text: '用户地区分布',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['用户地区分布']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: '用户地区分布',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    }
                }
            ]
        };
        //使用指定的配置项和数据显示图表
        myEcharts.setOption(option);//自己要定义
        $.ajax({
            url: "${cmfz}/user/findAllByProvince",
            datatype: "json",
            type: "post",
            success: function (da) {
                myEcharts.setOption({//只是为了动态改变数据
                    series: [{data: da}]
                });
            }
        })


        var goEasy = new GoEasy({appkey: "BC-efb8df40bb964af08c3d8c2af0e15861"});
        goEasy.subscribe({
            channel: "local_channel",
            onMessage: function (message) {
                var da = JSON.parse(message.content);//将JSON字符串转换成对象
                console.log(da);
                myEcharts.setOption({//只是为了动态改变数据
                    series: [{data: da}]
                });
            }
        });
        //用户7天注册走势图
        // 基于准备好的dom，初始化echarts实例
        var myEchartsRegist = echarts.init(document.getElementById("registDom"));
        //指定图表的配置项和数据
        var optionRegist = {
            title: {text: '持明法洲用户'},
            tooltip: {},
            legend: {data: ['日期']},
            xAxis: {
                data: []
            },
            yAxis: {},//可以不写，自适应
            series: [
                {
                    name: '日期',
                    //type:'pie',//饼状图  line:折线图
                    type: 'line'
                }
            ]
        }
        //使用指定的配置项和数据显示图表
        myEchartsRegist.setOption(optionRegist);//自己要定义
        $.ajax({
            url: "${cmfz}/user/findAllByDate",
            datatype: "json",
            type: "post",
            success: function (da) {
                //var data = JSON.parse(da);//将JSON字符串转换成对象
                console.log(da);
                var date = new Array();
                var va = new Array();
                for (var i = 0; i < 7; i++) {
                    console.log(da[i]);
                    date[i] = da[i].name;
                    va[i] = da[i].value;
                }
                console.log(date);
                console.log(va);
                myEchartsRegist.setOption({//只是为了动态改变数据
                    xAxis: {
                        data: date
                    },
                    series: [{data: va}]
                });
            }
        })

        var goEasy = new GoEasy({appkey: "BC-efb8df40bb964af08c3d8c2af0e15861"});
        goEasy.subscribe({
            channel: "my_channel",
            onMessage: function (message) {
                var data = JSON.parse(message.content);//将JSON字符串转换成对象
                console.log(data);
                var date = new Array();
                var va = new Array();
                for (var i = 0; i < 7; i++) {
                    console.log(data[i]);
                    date[i] = data[i].name;
                    va[i] = data[i].value;
                }
                console.log(date);
                console.log(va);
                myEchartsRegist.setOption({//只是为了动态改变数据
                    xAxis: {
                        data: date
                    },
                    series: [{data: va}]
                });
            }
        });
    })

    function exportUser() {
        window.location.href = "${cmfz}/user/exportUser";
    }
</script>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active" id="active"><a href="#home" aria-controls="home" role="tab"
                                                              data-toggle="tab">用户列表</a></li>
        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">用户地区分布</a></li>
        <li role="presentation"><a href="#messages" aria-controls="profile" role="tab" data-toggle="tab">用户注册走势</a></li>
    </ul>
</div>

<!-- Tab panes -->
<div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="home">
        <div style="height: 50px">
            <button type="button" class="btn btn-primary" onclick="exportUser()">导出用户信息</button>
        </div>
        <table id="userList"></table>
        <div id="userPager"></div>
    </div>
    <div role="tabpanel" class="tab-pane" id="profile">
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="localDom" style="width: 600px;height: 400px"></div>
    </div>
    <div role="tabpanel" class="tab-pane" id="messages">
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="registDom" style="width: 600px;height: 400px"></div>
    </div>
</div>