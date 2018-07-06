<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">

	<title>Blog post | Initio - Free, multipurpose html5 template by GetTemplate</title>

	<link rel="shortcut icon" href="assets/images/gt_favicon.png">

	<!-- Bootstrap -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.no-icons.min.css" rel="stylesheet">
	<!-- Icon font -->
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<!-- Custom styles -->
	<link rel="stylesheet" href="/BookAssitantSystem/resources/assert/css/styles.css">
	<link rel="stylesheet" href="/BookAssitantSystem/resources/assert/css/bookSystem.css" />

	<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>

	<!-- 引入vue -->
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	
	<script src="/BookAssitantSystem/resources/assert/js/myEncryption.js"></script>

</head>
<style>
	.margintop{
		margin-bottom: 200px;
	}
</style>
<body>

<!-- Logo与登录界面 -->
<header id="header">
	<div id="head" class="parallax" parallax-speed="1">

		<!--设置新书速递以及自动续借和续借全部书籍-->
		<ul class="setup">
			<li id="openNewBookPush">新书速递</li>
			<li id="openAutoBorrowPush">自动续借</li>
			<li id="openBorrowAllNoPush">续借全部书籍</li>
		</ul>

		<h1 id="logo" class="text-center">Szpt BookSystem</h1>
		<!--搜索条-->
		<div class="searchBar">
			<form action="/bookSystem/searchwithpymongo" method="GET">
				<select name="find_code" id="find_code">
				     <option value="AllKeyButNotCatalog" selected="">所有字段(除目录)</option>
				     <option value="AllKey">所有字段</option>
				     <option value="CatalogKey">目录</option>
				     <option value="BookNameKey">题名关键词</option>
				     <option value="BookAuthorKey">著者</option>
				     <option value="BookPublisherKey">出版社</option>
				     <option value="ISBNKey">ISBN</option>
				     <option value="IndexKey">索书号</option>
				     <option value="SystemNumberKey">系统号</option>
			    </select>
				<input id="searchInput" type="text" name="searchKey" value="{{ searchKey }}" />
				<input type="submit" value="检索" />
				<div id="operate">
					<span id="label">排序：</span>
					<select name="sort" id="sort">
						<option value="Year-Rating-Person" selected="">年/评分/评论人数(降序)</option>
						<option value="Rating-Year-Person">评分/年/评论人数(降序)</option>
						<option value="Person-Year-Rating">评论人数/年/评分(降序)</option>
					</select>
				</div>
			</form>
		</div>

		<!--登录界面,登录之前-->
		<!-- {% if request.session.isLogin %}
		<div class="login topy">
			<p>
				<strong class="vwmy"><a href="" target="_blank" title="访问我的空间">{{request.session.userName}}</a></strong>
				<span class="pipe">|</span><a href="javascript:;" id="myitem" class="showmenu" onmouseover="showMenu({'ctrlid':'myitem'});" initialized="true">我的</a>
				<span class="pipe">|</span><a href="/bookSystem/ttest">设置</a>
				<span class="pipe">|</span><a href="/bookSystem/quitLogin">退出</a>
			</p>
		</div>
		{% else %}
		<form action="/bookSystem/login" method="POST">
			{% csrf_token %}
			<div class="topy login">
				<table cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td><label for="ls_username">帐号</label></td>
							<td><input type="text" name="username" id="ls_username" class="px vm xg1" value="学号/Email" onfocus="if(this.value == '学号/Email'){this.value = '';this.className = 'px vm';}" onblur="if(this.value == ''){this.value = '学号/Email';}" tabindex="901"></td>
							<td class="fastlg_l"><label for="ls_cookietime"><input type="checkbox" name="cookietime" id="ls_cookietime" class="pc" value="2592000" tabindex="903">自动登录</label></td>
						</tr>
						<tr>
							<td><label for="ls_password">密码</label></td>
							<td><input type="password" name="password" id="ls_password" class="px vm" autocomplete="off" tabindex="902"></td>
							<td class="fastlg_l"><button type="submit" class="pn vm" tabindex="904" style="width: 75px;"><em>登录</em></button></td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="quickforward" value="yes">
				<input type="hidden" name="handlekey" value="ls">
			</div>
		</form>
		{% endif %}-->
	</div>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav">
					<li><a href="index.html">Home</a></li>
					<li><a href="blog.html">新书速递</a></li>
					<li class="active"><a href="about.html">About</a></li>
				</ul>

			</div>
		</div>
	</nav>
</header>

<main id="main">

	<div class="container margintop">

		<div class="row topspace">

			<center>
				<form action="/BookAssitantSystem/login/check" class="loginNew" id="loginFormModel">
					<div id="tipsMessageLogin">
						<span style="font-size: 30px;font-family: 'Courier New', Courier, monospace">登录</span><br>
						<span id="errorMsg" style="color: red">请输入正确用户名与密码</span>
					</div><br>
					<hr>
					<div id="usernameDiv">
						<div class="errorMsg"><span style="color: red">请输入与学校图书馆绑定的账号，如：162400xx</span></div>
						<span>用户名:</span><input name="userName" type="text"><br>
					</div>
					<hr>
					<div id="passwordDiv">
						<div class="errorMsg"><span style="color: red">图书馆默认密码为出生日期，如：19970x0x</span></div>
						<span>密码:</span><input name="password" type="password" /><br>
					</div>
					<hr>
					<div id="verificationCodeDiv">
						<div class="errorMsg"><span style="color: red">请回答语音验证码中提到的问题~</span></div>
						<span>语音验证码:</span>
						<input type="text" name="verificationCode"><br/>
						<audio src="http://localhost:8088/interface/getAudio?content=${qustionContent}" controls="controls">sad</audio> <br>
					</div>				
					<hr>
					<button type="submit">登录</button>
					<input name="token" type="hidden" v-bind:value="getToken()">
				</form>
			</center>
		</div>
	</div>	

</main>

<footer id="footer" class="topspace">
	<div class="container">
		<div class="row">

			<div class="col-md-3 widget">
				<h3 class="widget-title">Text widget</h3>
				<div class="widget-body">
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque, nihil natus explicabo ipsum quia iste aliquid repellat eveniet velit ipsa sunt libero sed aperiam id soluta officia asperiores adipisci maxime!</p>

				</div>
			</div>

			<div class="col-md-3 widget">
				<h3 class="widget-title">Form widget</h3>
				<div class="widget-body">
					<p>+234 23 9873237<br>
						<a href="mailto:#">some.email@somewhere.com</a><br>
						<br>
						234 Hidden Pond Road, Ashland City, TN 37015
					</p>
				</div>
			</div>

		</div> <!-- /row of widgets -->
	</div>
</footer>

</body>
</html>


<script src="/BookAssitantSystem/resources/assert/js/loginRender.js">
	
</script>