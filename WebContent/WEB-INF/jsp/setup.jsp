<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">

	<title>基本设置</title>

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

		<!--登录界面-->
		<c:if test="${sessionScope.isLogin}">
		<div class="login topy">
			<p>
				<strong class="vwmy"><a href="" target="_blank">${sessionScope.userName}</a></strong>
				<span class="pipe">|</span><a href="/BookAssitantSystem/setup">设置</a>
				<span class="pipe">|</span><a href="/BookAssitantSystem/login/quit">退出</a>
			</p>
		</div> 
		</c:if>
		<c:if test="${empty sessionScope.isLogin || sessionScope.isLogin==false}">
		<form action="/BookAssitantSystem/login" method="POST">
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
			</div>
		</form>
		</c:if>
	</div>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav">
					<li><a href="/BookAssitantSystem/bookSearch">Home</a></li>
					<li><a href="/BookAssitantSystem/newBook">新书速递</a></li>
					<li class="active"><a href="about.html">About</a></li>
				</ul>

			</div>
		</div>
	</nav>
</header>

<main id="main">

	<div class="container margintop">

		<div class="row topspace">

			<!-- Sidebar -->
			<aside class="col-sm-4 sidebar sidebar-right">

				<ul class="nav text-right nav-side">
					<li id="basesetup" v-bind:class="{'active':getActived()}" v-on:click="clickThis()"><a>基本设置</a></li>
					<li id="attention" v-bind:class="{'active':getActived()}" v-on:click="clickThis()"><a>特别关注</a></li>
					<li id="history" v-bind:class="{'active':getActived()}" v-on:click="clickThis()"><a>历史记录</a></li>
					<li id="freenotice" v-bind:class="{'active':getActived()}" v-on:click="clickThis()"><a >馆藏空闲通知</a></li>
				</ul>

			</aside>
			<!-- /Sidebar -->


			<!--此处是设置邮箱的地方-->
            <article class="col-sm-8 maincontent" id="baseSetUpModel" v-if="actived">
                <c:if test="${user.email!=null}">
                	<p style="color: red;">邮箱已设置，当前邮箱为:${email}</p>
                </c:if>
                <c:if test="${user.email==null}">
                	<p style="color: red;">您的邮箱还未进行设置，请进行设置，以便进行自动续借和新书速递的推送</p>
                </c:if>
				
				<form action="/BookAssitantSystem/setup/email" method="POST">
					邮箱:<input type="text" name="email" style="width: 300px;" size="30" value="${user.email}" />
					<input type="submit" value="设置" />
				</form>

				<div class="autoBorrowDiv">
					<span>是否开启自动续借功能</span>
					<select name="isOpenAutoBrrow" id="isOpenAutoBrrow">
						<c:if test="${isAutoBorrow}">
							<option value="${isAutoBorrow}" selected="selected">开启</option>
							<option value="${!isAutoBorrow}">关闭</option>
						</c:if>
						<c:if test="${!isAutoBorrow}">
							<option value="${!isAutoBorrow}" selected="selected">关闭</option>
							<option value="${isAutoBorrow}">开启</option>
						</c:if>
					</select>
					<span style="color:red;"></span>
				</div>
				<div class="newBookDiv">
					<span>是否开启新书速递功能</span>
					<select name="isOpenGetNewBook" id="isOpenGetNewBook" onclick="return false;">
						<c:if test="${isAutoNewBook}">
							<option value="${isAutoNewBook}" selected="selected">开启</option>
							<option value="${!isAutoNewBook}">关闭</option>
						</c:if>
						<c:if test="${!isAutoNewBook}">
							<option value="${!isAutoNewBook}" selected="selected">关闭</option>
							<option value="${isAutoNewBook}">开启</option>
						</c:if>	
					</select>
					<span style="color:red;"></span>
					
				</div>

			</article>

			<!-- 此处是设置特别关注的地方 -->
			<article class="col-sm-8 maincontent" id="specialkeysModel" v-if="actived">
				<p>设置我的特别关注标签，添加标签，在每月一次的新书速递中，如果遇到字段中含有特别关注的标签，将会给您定时推送。</p>
				<br/>
				<legend><span class="bookLabel">设置我的特别关注  · · · · · ·</span></legend>
				<div class="specialAttention">
					<c:forEach items="${specialKeys}" var="specialKey">
						<span class="specialLabel">${specialKey.specialKey}(${specialKey.keyType})</span>，
					</c:forEach>
				</div>
				<div class="addSpecialAttention">
					<form action="/BookAssitantSystem/setup/addSpeicalKey" method="POST">
						关键字:<input type="text" name="key" />
						包含关键字的字段:
						<select name="KeyCode" id="find_code2">
						     <option value="CatalogKey">目录</option>
						     <option value="BookNameKey">题名关键词</option>
						     <option value="BookAuthorKey">著者</option>
						     <option value="BookPublisherKey">出版社</option>
					    </select>
					    <input type="submit" value="添加特别关注标签" />
					</form>
				</div>
			</article>
			
			<!-- 历史记录 -->
			<!-- 使用vue渲染 -->
			<article class="col-sm-8 maincontent" id="historyModel" v-if="actived">
				<table class="historyModel" border="1">
					<tr>
						<td>类型</td>
						<td class="tdMeesage">消息</td>
						<td>创建时间</td>
					</tr>
					<template v-if="historyList.length>0">
						<template v-for="item in historyList">
							<tr>
								<td>{{item.type}}</td>
								<td class="tdMeesage">{{item.description}}</td>
								<td>{{new Date(item.createTime)}}</td>
							</tr>
						</template>
					</template>
					<template v-else>
						<tr>
							<td colspan="3">暂无历史记录</td>
						</tr>
					</template>
				</table>
				<span class="prePage" v-on:click="gotoPage(page-1)"><a>上一页</a></span>
				<span class="nextPage"  v-on:click="gotoPage(page+1)"><a>下一页</a></span>
				
			</article>

			<!-- 馆藏空闲通知 -->
			<!-- 使用vue渲染 -->
			<article class="col-sm-8 maincontent" id="freenoticeModel" v-if="actived">
				<table class="historyModel" border="1">
					<tr>
						<td>书名</td>
						<td>ISBN</td>
						<td>索书号</td>
					</tr>
					<template v-if="freeNoticeList.length > 0">				 	
						<template v-for="item in freeNoticeList">
							<tr>
								<td>{{item.bookName}}</td>
								<td>{{item.isbn}}</td>
								<td>{{item.bookIndex}}</td>
							</tr>
						</template>
					</template>
					<template v-else>
						<tr>
							<td colspan="3">暂无馆藏空闲通知</td>
						</tr>
					</template>
				</table>
				<span class="prePage" v-on:click="gotoPage(page-1)"><a>上一页</a></span>
				<span class="nextPage" v-on:click="gotoPage(page+1)"><a>下一页</a></span>
			</article>
			<!-- /Article -->


		</div>
	</div>	<!-- /container -->

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


<script src="/BookAssitantSystem/resources/assert/js/setupRender.js"></script>
<script> 
	// 设置vue模型的fromuserID
	historyModel.userID = ${user.userName};
	freenoticeModel.userID = ${user.userName};
	var setupActived = '${setupActived}';
	if(setupActived == "baseSetup"){
		baseSetUpModel.actived = true;
	}
	if(setupActived == "specialKey"){
		specialkeysModel.actived = true;
	}
	setTimeout(() => {
		updateHistory(0,'${user.userName}');
		updateFreeNotice(0,'${user.userName}');
	}, 20);
</script>