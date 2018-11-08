<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<title>Ứng dụng chat</title>
	</head>

	<link rel="stylesheet" type="text/css" href="/susu/css/registered.css"/>

	<body>

		<!--遮罩-->
		<div class="mask"></div>
		<div class="mask2"></div>
		<!--遮罩-->

		<!-- 主体开始 -->
		<div class="bodyer">
			<div class="panel">
				<div class='logo'></div>
				<h3 class="panel_tit">“Chat room</h3>
				<form action="/susu/admin/toLogin" method="post" class="register">
					<div class="register_top">
						<a href="#">Đăng nhập</a>
						<span>|</span>
						<a href="#">Đăng ký</a>
					</div>
					<div class="user_icon"><i></i>
						<input type="text" placeholder="Tên tài khoản"  name="userName" id="userName" value="${userName!''}" /></div>
					<div class="pass_icon">
						<i></i>
						<input type="password" placeholder="Mật khẩu" name="passWord" id="passWord" value="${passWord!''}" />
						<div class="Span">
							<span>Hãy nhập tên tài khoản</span>
							<span>2-5Việt nam</span>
							<span>Độ dài tối đa là 6 - 16</span>
							<span>Mật khẩu không đúng!</span>
							<#--<span>${msg!''}</span>-->
						</div>
					</div>
					<input type="submit" class="btn1" value='Login' />
                    <!--提示密码错误或用户名错误-->
                    <div class="tips">${msg!''}</div>
				<form>
			</div>
		</div>
		<!-- 主体结束 -->
	</body>
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src='/susu/js/registered.js'></script>
	<script>
        if("${msg!''}" != ''){
        MsgTo();
        }
    </script>
</html>