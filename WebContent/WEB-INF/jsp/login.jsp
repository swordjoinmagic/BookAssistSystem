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
		<div class="searchBar" id="searchInputModel">
			<form action="/BookAssitantSystem/bookSearch" method="GET">
				<select name="searchType" id="find_code" v-model="searchType">
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
				<input id="searchInput" type="text" name="queryContent" v-model="queryContent" />
				<input type="submit" value="检索" />
				<div id="operate">
					<span id="label">排序：</span>
					<select name="sortType" id="sort" v-model="sortType">
						<option value="0" selected="">年/评分/评论人数(降序)</option>
						<option value="1">评分/年/评论人数(降序)</option>
						<option value="2">评论人数/年/评分(降序)</option>
					</select>
				</div>
			</form>
		</div>


	</div>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav">
					<li><a href="/BookAssitantSystem/bookSearch">Home</a></li>
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
					<div class="tipsMessageLogin">
						<span style="font-size: 30px;font-family: 'Courier New', Courier, monospace">登录</span><br>
						<span id="errorMsg" style="color: red">{{errorMsg}}</span>
					</div>
					<br>
					<hr>
					<div class="usernameDiv">
						<div class="errorMsg"><span style="color: red">请输入与学校图书馆绑定的账号，如：162400xx</span></div>
						<span>用户名:</span>
						<input name="userName" type="text" v-model="userName"><br>
					</div>
					<hr>
					<div class="passwordDiv">
						<div class="errorMsg"><span style="color: red">图书馆默认密码为出生日期，如：19970x0x</span></div>
						<span>密码:</span>
						<input name="password" type="password" v-model="password" /><br>
					</div>
					<hr>
					<div class="verificationCodeDiv">
						<div class="errorMsg"><span style="color: red">请回答语音验证码中提到的问题~</span></div>
						<span>语音验证码:</span>
						<input type="text" name="verificationCode" v-model="verificationCode"><br/>
						<audio src="http://localhost:8088/interface/getAudio?content=${qustionContent}" controls="controls">sad</audio> <br>
					</div>			
					<hr>
					<button type="submit" onclick="return false;" v-on:click="getLoginCheck()">登录</button>
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


<script src="/BookAssitantSystem/resources/assert/js/loginRender.js"></script>
<script>
	loginFormModel.userName = '${userName}';
	loginFormModel.password = '${password}';
</script>