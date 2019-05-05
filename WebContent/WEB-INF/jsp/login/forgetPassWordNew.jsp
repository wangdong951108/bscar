<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>忘记密码页面</title>
<link href="${ctx}/resource/common/css/bootstrap.css" rel="stylesheet"
	type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${ctx}/resource/common/js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="${ctx}/resource/common/css/style.css" rel="stylesheet"
	type="text/css" media="all" />
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript">addEventListener("load", function() {
		setTimeout(hideURLbar, 0);
	}, false);

	function hideURLbar() {
		window.scrollTo(0, 1);
	}
</script>
<!--fonts-->
<!--//fonts-->
<script type="text/javascript"
	src="${ctx}/resource/common/js/move-top.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/easing.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event) {
			event.preventDefault();
			$('html,body').animate({
				scrollTop : $(this.hash).offset().top
			}, 1000);
		});
	});
</script>
<!--slider-script-->
<script src="${ctx}/resource/common/js/responsiveslides.min.js"></script>
<script>
	$(function() {
		$("#slider1").responsiveSlides({
			auto : true,
			speed : 500,
			namespace : "callbacks",
			pager : true,
		});
	});
</script>
<!--//slider-script-->
<script>$(document).ready(function(c) {
		$('.alert-close').on('click', function(c) {
			$('.message').fadeOut('slow', function(c) {
				$('.message').remove();
			});
		});
	});
</script>
<script>$(document).ready(function(c) {
		$('.alert-close1').on('click', function(c) {
			$('.message1').fadeOut('slow', function(c) {
				$('.message1').remove();
			});
		});
	});
</script>
</head>
<body>
	<%@ include file="/common/menu.jsp"%>
	<div class="container">
		<div class="account">
			<h2 class="account-in">忘记密码</h2>
			<div>
				<tr>
					<span>手机号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<input type="text" name="phone" id="phone">
				</tr>
				<tr>
					<td align="center"><span id="Phonepk"></span></td>
				</tr>
			</div>
			<div>
				<tr>
					<span>输入验证码&nbsp;&nbsp;&nbsp;</span>
					<input type="text" name="codelast" id="codelast">
				</tr>
				<tr>
					<td align="center"><span id="jbPhoneTip"></span></td>
				</tr>
			</div>
			<input type="submit" value="提交" style="margin-left: 129px;"
				onclick="chagecode()" /> <input id="btnSendCode" name="btnSendCode"
				type="submit" value="点击获取验证码" style="margin-left: 129px;"
				onclick="sendMessage()" />

		</div>
	</div>
	<%-- <div class="footer" style="position: fixed;bottom: 0px;width: 100%">
		<p class="footer-class">Copyright &copy; 2019.Company name All rights reserved.</p>
		<script type="text/javascript">
					$(document).ready(function() {
						$().UItoTop({ easingType: 'easeOutQuart' });
						
					});
				</script>
			<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
		</div>	--%>
</body>
<script>

	var InterValObj;
	//timer变量，控制时间
	var count = 60; //间隔函数，1秒执行
	var curCount; //当前剩余秒数
	function sendMessage() {
		var phone = $("#phone").val();
		if (!(/^1[34578]\d{9}$/.test(phone))) {
			$("#Phonepk").html("<font color='red'>请输入有效的手机号码！</font>");
			return;
		} else {
			$("#Phonepk").html("<font color='red'> </font>");
			if (phone == "" || phone == null) {
				$("#Phonepk").html("<font color='red'>请填写手机号</font>");
				return;
			} else {
				$("#Phonepk").html("<font color='red'> </font>");
				curCount = count; // 设置button效果，开始计时
				document.getElementById("btnSendCode").setAttribute("disabled", "true"); //设置按钮为禁用状态
				document.getElementById("btnSendCode").value = "请在" + curCount + "后再次获取"; //更改按钮文字
				InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器timer处理函数，1秒执行一次 // 向后台发送处理数据
				$.ajax({
					type : "POST", // 用POST方式传输
					dataType : "json", // 数据格式:JSON
					url : ctx + '/user_getCode.do', // 目标地址
					data : {
						phone : phone
					},
					success : function(data) {
						if (data.data == 2) {
							$("#jbPhoneTip").html("<font color='#339933'>√ 短信验证码已发到您的手机,请查收</font>");
						} else if (data.data == 0) {
							$("#jbPhoneTip").html("<font color='red'>× 短信验证码发送失败，请重新发送</font>");
						} else if (data.data == 1) {
							$("#jbPhoneTip").html("<font color='red'>此号码1小时内发送次数应小于4次</font>");
						} else if (data.data == 3) {
							$("#jbPhoneTip").html("<font color='red'>请您先注册！</font>");
						}
					}
				});
			}
		}
	}

	//timer处理函数
	function SetRemainTime() {
		if (curCount == 0) {
			window.clearInterval(InterValObj); // 停止计时器
			document.getElementById("btnSendCode").removeAttribute("disabled"); //移除禁用状态改为可用
			document.getElementById("btnSendCode").value = "重新发送验证码";
		} else {
			curCount--;
			document.getElementById("btnSendCode").value = "请在" + curCount + "秒后再次获取";
		}
	}
	//验证码验证
	function chagecode() {
		var phone = $("#phone").val();
		var btnSendCode = $("#codelast").val();

		if (!(/^1[34578]\d{9}$/.test(phone))) {
			$("#Phonepk").html("<font color='red'>请输入有效的手机号码！</font>");
			return;
		} else {
			$("#Phonepk").html("<font color='red'> </font>");
			if (phone == "" || phone == null) {
				$("#Phonepk").html("<font color='red'>请填写手机号</font>");
				return;
			} else {
				$("#Phonepk").html("<font color='red'> </font>");
				if (btnSendCode == "" || btnSendCode == null) {
					$("#jbPhoneTip").html("<font color='red'>请填写验证码</font>");
					return;
				}
				$("#jbPhoneTip").html("<font color='red'></font>");
				$.ajax({
					type : "POST", // 用POST方式传输
					dataType : "json", // 数据格式:JSON
					url : ctx + '/user_changeCode12.do', // 目标地址"
					data : {
						phone : phone,
						passWord : btnSendCode
					},
					success : function(data) {
						if (data.data == 2) {
							window.location.href = "${ctx}/user_updatePassWord.do";
						} else if (data.data == 1) {
							$("#jbPhoneTip").html("<font color='red'>×短信验证码不正确请重新输入</font>");
						} else if (data.data == 0) {
							$("#jbPhoneTip").html("<font color='red'>×请输入验证码</font>");
						}
					}
				})

			}
		}

	}
</script>
</html>