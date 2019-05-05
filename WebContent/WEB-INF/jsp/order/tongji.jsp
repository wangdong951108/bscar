<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!-- 评价界面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台管理系统</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resource/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resource/css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resource/css/style.css" />
<link rel="stylesheet"
	href="${ctx}/resource/css/jquery.datetimepicker.css">
<script type="text/javascript" src="${ctx}/resource/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/ckform.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>
<script type="text/javascript"
	src="${ctx}/resource/js/echarts/echarts-all.js"></script>
<script src="${ctx}/resource/js/jquery.datetimepicker.js"></script>
<script src="${ctx}/resource/js/echarts.js"></script>
<script src="${ctx}/resource/js/echarts1.js"></script>
</head>
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}

td {
	text-align: center;
}
</style>
<body>
	<table class="table table-bordered table-hover definewidth m10"
		align="center">
		<thead>
			<tr>
				<th>月份</th>
				<th>利润</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="list" varStatus="l">
			<tr>
				<c:if test="${list.month!=null}">
					<td>${list.month}</td>
					<td>￥${list.price}</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	<div id="main" style="width:80%;height: 70%"></div>


	<!--  <div class="panel-foot text-center"> -->
	<%--       <pg:pager  url="${ctx}/order_order.do" maxIndexPages="5" items="${pagers.total}"  maxPageItems="15" export="curPage=pageNumber" > --%>
	<%-- 		<pg:last>   --%>
	<%-- 			共${pagers.total}记录,共${pageNumber}页,   --%>
	<%-- 		</pg:last>   --%>
	<%-- 			当前第${curPage}页  --%>

	<%--         <pg:first>   --%>
	<%--     		<a href="${pageUrl}">首页</a>   --%>
	<%-- 		</pg:first>   --%>
	<%-- 		<pg:prev>   --%>
	<%--     		<a href="${pageUrl}">上一页</a>   --%>
	<%-- 		</pg:prev>   --%>

	<%--        	<pg:pages>   --%>
	<%--         	<c:choose>   --%>
	<%--             	<c:when test="${curPage eq pageNumber}">   --%>
	<%--                 	<font color="red">[${pageNumber }]</font>   --%>
	<%--             	</c:when>   --%>
	<%--             	<c:otherwise>   --%>
	<%--                		<a href="${pageUrl}">${pageNumber}</a>   --%>
	<%--             	</c:otherwise>   --%>
	<%--         	</c:choose>   --%>
	<%--     	</pg:pages> --%>

	<%--         <pg:next>   --%>
	<%--     		<a href="${pageUrl}">下一页</a>   --%>
	<%-- 		</pg:next>   --%>
	<%-- 		<pg:last>   --%>
	<%-- 			<c:choose>   --%>
	<%--             	<c:when test="${curPage eq pageNumber}">   --%>
	<!--                 	<font color="red">尾页</font>   -->
	<%--             	</c:when>   --%>
	<%--             	<c:otherwise>   --%>
	<%--                		<a href="${pageUrl}">尾页</a>   --%>
	<%--             	</c:otherwise>   --%>
	<%--         	</c:choose>  --%>

	<%-- 		</pg:last> --%>
	<%-- 	</pg:pager> --%>
	<!--     </div> -->

	<div id="main" style="width: 600px;height:400px;"></div>
</body>
<script>
	$(function() {
		$('#add').click(function() {
			window.location.href = "${ctx}/order_add.do";
		});
	});
	//未a通过审批
	$(function() {
		$('#unAgreeOrder').click(function() {
			window.location.href = "${ctx}/order_unAgreeOrder.do";
		});
	});
	//未完成交易
	$(function() {
		$('#unFinishOrder').click(function() {
			window.location.href = "${ctx}/order_unFinishOrder.do";
		});
	});
	//加载日期插件
	$('#startTime').datetimepicker({
		timepicker : false,
		format : 'Y-m-d',
		formatDate : 'Y-m-d',
		lang : 'ch',
	});
	//加载日期插件
	$('#endTime').datetimepicker({
		timepicker : false,
		format : 'Y-m-d',
		formatDate : 'Y-m-d',
		lang : 'ch',
	});


	$(function() {
		var price = [];
		var month = [];
		$.ajax({
			url : '${ctx}/order_tongjitu.do',
			type : 'post',
			dataType : 'json',
			success : function(data) {

				for (var i = 0; i < data.length; i++) {
					price[i] = data[i].price;
					month[i] = data[i].month;
				}
				var myChart = echarts.init(document.getElementById('main'));
				var app = {};
				option = null;
				option = {
					title : {
						text : '折线图堆叠'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [ '利润', '联盟广告' ]
					},
					grid : {
						left : '3%',
						right : '4%',
						bottom : '3%',
						containLabel : true
					},
					toolbox : {
						show : true,
						feature : {
							mark : {
								show : true
							},
							dataZoom : {
								show : true
							},
							dataView : {
								show : true
							},
							magicType : {
								show : true,
								type : [ 'line', 'bar', 'stack',
									'tiled' ]
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					dataZoom : {
						show : true,
						realtime : true,
						start : 20,
						end : 90
					},
					xAxis : {
						type : 'category',
						boundaryGap : false,
						name : '日期',
						data : month
					},
					yAxis : {
						type : 'value',
						name : '金额'
					},
					series : [ {
						name : '金额',
						type : 'line',
						data : price
					} ]
				};
				if (option && typeof option === "object") {
					myChart.setOption(option, true);
				}
			}
		});


	});
</script>