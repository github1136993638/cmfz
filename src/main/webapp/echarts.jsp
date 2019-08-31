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
    <script src="${cmfz}/echarts/echarts.min.js"></script>
    <script src="${cmfz}/echarts/china.js"></script>
    <script src="${cmfz}/boot/js/jquery-2.2.1.min.js"></script>

</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="dom" style="width: 600px;height: 400px"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myEcharts = echarts.init(document.getElementById("dom"));
    //指定图表的配置项和数据
    var option = {
        title: {text: 'Echarts入门实例'},
        tooltip: {},
        legend: {data: ['地区']},
        xAxis: {
            data: ["河南", "江苏", "湖南", "陕西"]
        },
        yAxis: {},//可以不写，自适应
        series: [
            {
                name: '地区',
                //type:'pie',//饼状图  line:折线图
                type: 'bar'
                //data:[5,10,30,20,10,60]
            }
            /*{
                name:'库存',
                //type:'pie',//饼状图  line:折线图
                type:'line',
                data:[5,10,30,20,10,60]
            }*/
        ]
    }

    /*option = {
        title : {
            text: '用户地区分布',
            left: 'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data:['iphone3']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            left: 'right',
            top: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name: 'iphone3',
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
                /!*data:[
                    {name: '北京',value: Math.round(Math.random()*1000)},
                    {name: '天津',value: Math.round(Math.random()*1000)},
                    {name: '上海',value: Math.round(Math.random()*1000)},
                    {name: '重庆',value: Math.round(Math.random()*1000)},
                    {name: '河北',value: Math.round(Math.random()*1000)},
                    {name: '河南',value: Math.round(Math.random()*1000)},
                    {name: '云南',value: Math.round(Math.random()*1000)},
                    {name: '辽宁',value: Math.round(Math.random()*1000)},
                    {name: '黑龙江',value: Math.round(Math.random()*1000)},
                    {name: '湖南',value: Math.round(Math.random()*1000)},
                    {name: '安徽',value: Math.round(Math.random()*1000)},
                    {name: '山东',value: Math.round(Math.random()*1000)},
                    {name: '新疆',value: Math.round(Math.random()*1000)},
                    {name: '江苏',value: Math.round(Math.random()*1000)},
                    {name: '浙江',value: Math.round(Math.random()*1000)},
                    {name: '江西',value: Math.round(Math.random()*1000)},
                    {name: '湖北',value: Math.round(Math.random()*1000)},
                    {name: '广西',value: Math.round(Math.random()*1000)},
                    {name: '甘肃',value: Math.round(Math.random()*1000)},
                    {name: '山西',value: Math.round(Math.random()*1000)},
                    {name: '内蒙古',value: Math.round(Math.random()*1000)},
                    {name: '陕西',value: Math.round(Math.random()*1000)},
                    {name: '吉林',value: Math.round(Math.random()*1000)},
                    {name: '福建',value: Math.round(Math.random()*1000)},
                    {name: '贵州',value: Math.round(Math.random()*1000)},
                    {name: '广东',value: Math.round(Math.random()*1000)},
                    {name: '青海',value: Math.round(Math.random()*1000)},
                    {name: '西藏',value: Math.round(Math.random()*1000)},
                    {name: '四川',value: Math.round(Math.random()*1000)},
                    {name: '宁夏',value: Math.round(Math.random()*1000)},
                    {name: '海南',value: Math.round(Math.random()*1000)},
                    {name: '台湾',value: Math.round(Math.random()*1000)},
                    {name: '香港',value: Math.round(Math.random()*1000)},
                    {name: '澳门',value: Math.round(Math.random()*1000)}
                ]*!/
            }
        ]
    };*/

    //使用指定的配置项和数据显示图表
    myEcharts.setOption(option);//自己要定义
    $.ajax({
        url: "/user/findAllByProvince",
        datatype: "json",
        type: "post",
        success: function (da) {
            myEcharts.setOption({//只是为了动态改变数据
                series: [{data: da}]
            });
        }
    })
    /*$.ajax({
        url:"/echarts/findEchartsMap",
        datatype:"json",
        type:"post",
        success:function (da) {
            myEcharts.setOption({//只是为了动态改变数据
                series:[{data:da}]
            });
        }
    })*/
</script>
</body>
</html>
